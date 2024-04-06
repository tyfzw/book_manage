package com.tyfzw.booksmanage.gateway.api.web.controller;

import cn.hutool.system.UserInfo;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import com.tyfzw.booksmanage.application.handler.BookHandler;
import com.tyfzw.booksmanage.application.handler.BorrowHandler;
import com.tyfzw.booksmanage.domain.bo.*;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.BaseController;
import com.tyfzw.booksmanage.gateway.api.web.request.*;
import com.tyfzw.booksmanage.gateway.api.web.response.*;
import com.tyfzw.booksmanage.infrastructure.common.utils.ValidationUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 8:42
 */
@Controller
@RequestMapping("managebooks")
@Slf4j
public class ManageController extends BaseController {


    @Autowired
    private BookHandler bookHandler;

    @Autowired
    private BorrowHandler borrowHandler;


    @GetMapping(value = "/bookInfo")
    public String bookList() {
        return "user_bookinfo";
    }


    /**
     * 用户端图书查询
     * @param model
     * @param request
     * @param isbn
     * @param name
     * @param writer
     * @param page
     * @param rows
     * @return
     */
    @PostMapping("/bookList")
    @ResponseBody
    public DataGridDataSource<BookVO> bookInfoDetail(Model model, HttpServletRequest request, @RequestParam(value = "bookIsbn", required = false, defaultValue = "") String isbn, @RequestParam(value = "bookName", required = false, defaultValue = "") String name, @RequestParam(value = "bookAuthor", required = false, defaultValue = "") String writer, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page, @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        model.addAttribute("user", getUserInfo(request));
        BookListBO bookListBO = new BookListBO();
        bookListBO.setIsbn(isbn);
        bookListBO.setName(name);
        bookListBO.setWriter(writer);
        bookListBO.setPage(page);
        bookListBO.setRows(rows);
        return bookHandler.selectUserBookByCriteria(bookListBO);
    }


    @GetMapping(value = "/booklist")
    public String listBooks(String currentPage,Model model, HttpServletRequest request) {
        model.addAttribute("user", getUserInfo(request));
        BookBO bookBO = new BookBO();
        bookBO.setPageNum(currentPage);
        PageBO<List<BookVO>> pageBO = bookHandler.selectTotalBooks(bookBO);
        PageResponse<List<BookVO>> pageResponse = new PageResponse<>();
        pageResponse.setCurrentPage(pageBO.getPageNum());
        pageResponse.setCount(pageBO.getCount());
        pageResponse.setPageCount(pageBO.getPageCount());
        pageResponse.setData(pageBO.getData());
        model.addAttribute("list", pageResponse);
        return "user_booklist";
    }

    /**
     * 预约书本
     *
     * @param model
     * @param request
     * @param orderRequest
     * @return
     */
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<OrderResponse> orderBook(Model model, HttpServletRequest request, @RequestBody OrderRequest orderRequest) {
        model.addAttribute("user", getUserInfo(request));
        ValidationUtils.validate(orderRequest);
        OrderBO orderBO = new OrderBO();
        BeanUtils.copyProperties(orderRequest, orderBO);
        boolean flag = bookHandler.order(orderBO, getUserInfo(request));
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setSuccess(flag);
        return ResponseResult.success(orderResponse);
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchBook(String currentPage,Model model, HttpServletRequest request) {
        model.addAttribute("user", getUserInfo(request));
        SearchBO searchBO = new SearchBO();
        searchBO.setName(request.getParameter("name"));
        searchBO.setPageNum(currentPage);
        PageBO<List<BookVO>> pageBO = bookHandler.searchBook(searchBO);
        PageResponse<List<BookVO>> pageResponse = new PageResponse<>();
        BeanUtils.copyProperties(pageBO, pageResponse);
        pageResponse.setCurrentPage(pageBO.getPageNum());
        model.addAttribute("list", pageResponse);
        return "user_booklist";
    }

    /**
     * 预约详情
     */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String orderDetail(Model model, HttpServletRequest request) {
        UserVO userVO = getUserInfo(request);
        model.addAttribute("user", userVO);
        List<BorrowVO> borrowVOS = bookHandler.orderDetail(userVO.getUserId());
        model.addAttribute("list", borrowVOS);
        return "user_reservation";
    }

    @RequestMapping(value = "/borrowDetail", method = RequestMethod.GET)
    public String borrowDetail(Model model, HttpServletRequest request) {
        UserVO userVO = getUserInfo(request);
        model.addAttribute("user", userVO);
        List<BorrowVO> borrowVOS = borrowHandler.borrowDetail(userVO.getUserId());
        model.addAttribute("list", borrowVOS);
        return "user_borrow";
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<RemoveResponse> remove(Model model, HttpServletRequest request, @RequestBody RemoveRequest removeRequest) {
        ValidationUtils.validate(removeRequest);
        model.addAttribute(getUserInfo(request));
        RemoveBO removeBO = new RemoveBO();
        BeanUtils.copyProperties(removeRequest, removeBO);
        boolean flag = borrowHandler.removeBorrow(removeBO);
        RemoveResponse removeResponse = new RemoveResponse();
        removeResponse.setSuccess(flag);
        return ResponseResult.success(removeResponse);
    }

}
