package com.tyfzw.booksmanage.gateway.api.web.controller;

import com.tyfzw.booksmanage.application.handler.BookHandler;
import com.tyfzw.booksmanage.application.handler.BorrowHandler;
import com.tyfzw.booksmanage.domain.bo.*;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.gateway.api.web.BaseController;
import com.tyfzw.booksmanage.gateway.api.web.request.*;
import com.tyfzw.booksmanage.gateway.api.web.response.*;
import com.tyfzw.booksmanage.infrastructure.common.utils.ValidationUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 20:23
 */
@Controller
@RequestMapping("admin")
@Slf4j
public class AdminManageController extends BaseController {

    @Autowired
    private BookHandler bookHandler;

    @Autowired
    private BorrowHandler borrowHandler;


    @GetMapping(value = "/borrowNow")
    public String bookBorrow() {
        return "admin_book_borrow";
    }

    @GetMapping(value = "/bookInfo")
    public String bookList() {
        return "admin_bookinfo";
    }

    @GetMapping(value = "/userInfo")
    public String user() {
        return "admin_user";
    }

    @GetMapping(value = "/books")
    public String showBook(String currentPage,Model model, HttpServletRequest request) {
        model.addAttribute("user", getUserInfo(request));
        BookBO bookBO = new BookBO();
        bookBO.setPageNum(currentPage);
        PageBO<List<BookVO>> pageBO = bookHandler.selectAdminTotalBooks(bookBO);
        PageResponse<List<BookVO>> pageResponse = new PageResponse<>();
        BeanUtils.copyProperties(pageBO, pageResponse);
        pageResponse.setCurrentPage(pageBO.getPageNum());
        model.addAttribute("list", pageResponse);
        return "admin_addbook";

    }

    @ResponseBody
    @PostMapping("/books")
    public ResponseResult<AddResponse> addBook(HttpServletRequest request,@RequestBody AddRequest addRequest) {
        ValidationUtils.validate(addRequest);
        AddBO addBO = new AddBO();
        BeanUtils.copyProperties(addRequest, addBO);
        boolean flag = bookHandler.addBook(addBO);
        AddResponse addResponse = new AddResponse();
        addResponse.setSuccess(flag);
        return ResponseResult.success(addResponse);
    }

    /**
     * @param
     * @return : io.hailiang.web.book.common.JsonData
     * @author: luhailiang
     * @date: 2019-04-15 14:17
     * @description: 编辑图书
     */
    @PutMapping("/update")
    @ResponseBody
    public ResponseResult<UpdateResponse> updateBookInfo(UpdateRequest updateRequest) {
        boolean flag = bookHandler.updateBook(updateRequest);
        UpdateResponse updateResponse = new UpdateResponse();
        updateResponse.setSuccess(flag);
        return ResponseResult.success(updateResponse);
    }


    @ResponseBody
    @DeleteMapping("/delete")
    public ResponseResult<DeleteResponse> deleteBook(Model model, HttpServletRequest request, String bookId) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.setBookId(bookId);
        ValidationUtils.validate(deleteRequest);
        DeleteBO deleteBO = new DeleteBO();
        BeanUtils.copyProperties(deleteRequest, deleteBO);
        boolean flag = bookHandler.deleteBook(deleteBO);
        DeleteResponse deleteResponse = new DeleteResponse();
        deleteResponse.setSuccess(true);
        return ResponseResult.success(deleteResponse);
    }


    @GetMapping(value = "/orderDetail")
    public String OrderDetail(Model model, HttpServletRequest request) {
        model.addAttribute("user", getUserInfo(request));
        List<BorrowVO> borrowVOS = borrowHandler.getOrderDetail();
        model.addAttribute("list", borrowVOS);
        return "admin_processreservation";
    }

    @ResponseBody
    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public ResponseResult<OrderResponse> agreeOrder(Model model, HttpServletRequest request, @RequestBody AdminOrderRequest adminOrderRequest) {
        model.addAttribute("user", getUserInfo(request));
        ValidationUtils.validate(adminOrderRequest);
        AdminOrderBO adminOrderBO = new AdminOrderBO();
        BeanUtils.copyProperties(adminOrderRequest, adminOrderBO);
        boolean flag = borrowHandler.agreeOrder(adminOrderBO);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setSuccess(flag);
        return ResponseResult.success(orderResponse);
    }

    @GetMapping(value = "/borrowDetail")
    public String showBorrow(Model model,HttpServletRequest request) {
        model.addAttribute("user", getUserInfo(request));
        List<BorrowVO> borrowVOS = borrowHandler.adminBorrowDetail();
        model.addAttribute("list", borrowVOS);
        return "admin_borrow";
    }


    @RequestMapping(value = "/ret", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<RetResponse> returnBook(Model model, HttpServletRequest request, @RequestBody RetRequest retRequest) {
        model.addAttribute("user", getUserInfo(request));
        ValidationUtils.validate(retRequest);
        RetBO retBO = new RetBO();
        BeanUtils.copyProperties(retRequest, retBO);
        boolean flag = borrowHandler.returnBook(retBO);
        RetResponse retResponse = new RetResponse();
        retResponse.setSuccess(flag);
        return ResponseResult.success(retResponse);
    }


    /**
     * 图书信息
     * @param bookId
     * @return
     */
    @PostMapping("/info")
    @ResponseBody
    public ResponseResult<BookResponse> bookInfo(String bookId) {
        BookResponse bookResponse = borrowHandler.bookInfo(bookId);
        return ResponseResult.success(bookResponse);
    }


    @PostMapping("/borrowBook")
    @ResponseBody
    public ResponseResult<BorrowResponse> borrowBook(Model model, HttpServletRequest request, BorrowRequest borrowRequest)  {
        model.addAttribute("user", getUserInfo(request));
        ValidationUtils.validate(borrowRequest);
        BorrowBO borrowBO = new BorrowBO();
        BeanUtils.copyProperties(borrowRequest, borrowBO);
        boolean flag = borrowHandler.borrowBook(borrowBO);
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.setSuccess(flag);
        return ResponseResult.success(borrowResponse);
    }


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
        return bookHandler.selectBookByCriteria(bookListBO);
    }

    @GetMapping("/detail")
    @ResponseBody
    public ResponseResult<BookVO> bookInfoDetail(String bookId) {
        BookVO bookVO = bookHandler.selectBookDetail(bookId);
        return ResponseResult.success(bookVO);
    }

}
