package com.tyfzw.booksmanage.application.handler;

import com.tyfzw.booksmanage.application.service.BookService;
import com.tyfzw.booksmanage.application.service.BorrowService;
import com.tyfzw.booksmanage.domain.bo.*;
import com.tyfzw.booksmanage.domain.vo.BookVO;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.domain.vo.UserVO;
import com.tyfzw.booksmanage.gateway.api.web.request.UpdateRequest;
import com.tyfzw.booksmanage.gateway.api.web.response.DataGridDataSource;
import com.tyfzw.booksmanage.infrastructure.common.enums.BookStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.BorrowStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import com.tyfzw.booksmanage.infrastructure.common.utils.CalendarUtils;
import com.tyfzw.booksmanage.infrastructure.common.utils.MyBeanUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookListDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDetailDO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 9:19
 */
@Service
public class BookHandler {


    @Autowired
    private BookService bookService;

    @Autowired
    private BorrowService borrowService;

    public PageBO<List<BookVO>> selectTotalBooks(BookBO bookBO) {
        PageBO<List<BookVO>> pageBO = bookService.selectTotalBooks(bookBO.getPageNum(), bookBO.getPageSize());
        if (Objects.isNull(pageBO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        return pageBO;
    }

    @Transactional
    public boolean order(OrderBO orderBO, UserVO userVO) {
        BookDO bookDO = bookService.selectBookById(orderBO.getBookId());
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        if (BookStatusEnum.DELETE.name().equals(bookDO.getStatus())) {
            throw new BizException(ResultCode.BOOK_ALREADY_DELETE);
        }
        if (BookStatusEnum.NO_BORROW.name().equals(bookDO.getStatus())) {
            throw new BizException(ResultCode.BOOK_NO_BORROW);
        }
        if ("YES".equals(bookDO.getBorrowFlag())) {
            throw new BizException(ResultCode.BOOK_ALREADY_BORROW);
        }
        int i = bookService.order(orderBO.getBookId());
        if (i != 1) {
            throw new BizException(ResultCode.BOOKS_BORROW_FAIL);
        }
        borrowService.insertBorrow(orderBO.getBookId(), userVO.getUserId());
        return true;
    }

    public PageBO<List<BookVO>> searchBook(SearchBO searchBO) {
        PageBO<List<BookVO>> pageBO  = bookService.selectBooksLikeName(searchBO.getPageNum(), searchBO.getPageSize(), searchBO.getName());
        if (Objects.isNull(pageBO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        return pageBO;
    }

    public List<BorrowVO> orderDetail(String userId) {
        List<BorrowDetailDO> borrowDetailDOS = borrowService.selectOrderByUserId(userId);
        for (BorrowDetailDO borrowDetailDO : borrowDetailDOS) {
            if (BorrowStatusEnum.ACCEPT.name().equals(borrowDetailDO.getStatus())) {
                if (Objects.isNull(borrowDetailDO.getOrderTime())) {
                    throw new BizException(ResultCode.OUT_TIME_NOT_FIND);
                }
                if (borrowDetailDO.getOrderTime().compareTo(new Date()) <= 0) {
                    int i = borrowService.updateStatus(borrowDetailDO.getBorrowId(),borrowDetailDO.getStatus(), BorrowStatusEnum.ACCEPT_OUT_TIME.name());
                    if (i != 1) {
                        throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                    }
                    BookDO bookDO = bookService.selectBookById(borrowDetailDO.getBookId());
                    if (Objects.isNull(bookDO)) {
                        throw new BizException(ResultCode.BOOKS_NOT_FIND);
                    }
                    int j = bookService.updateFlagByBookId(borrowDetailDO.getBookId(), bookDO.getBorrowFlag());
                    if (j != 1) {
                        throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                    }
                    borrowDetailDO.setUpdateTime(new Date());
                    borrowDetailDO.setStatus(BorrowStatusEnum.ACCEPT_OUT_TIME.name());
                }

            }
        }
        return MyBeanUtils.BorrowDetailDOToBorrowVO(borrowDetailDOS);
    }

    public PageBO<List<BookVO>> selectAdminTotalBooks(BookBO bookBO) {
        PageBO<List<BookVO>> pageBO = bookService.selectAdminTotalBooks(bookBO.getPageNum(), bookBO.getPageSize());
        if (Objects.isNull(pageBO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        return pageBO;
    }

    public boolean addBook(AddBO addBO) {
        bookService.insertBook(addBO);
        return true;
    }

    public boolean deleteBook(DeleteBO deleteBO) {
        BookDO bookDO = bookService.selectBookById(deleteBO.getBookId());
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        int i = bookService.deleteBookById(deleteBO.getBookId());
        if (i != 1) {
            throw new BizException(ResultCode.BOOK_DELETE_FAIL);
        }
        return false;
    }


    public DataGridDataSource<BookVO> selectBookByCriteria(BookListBO bookListBO) {
        BookListDO<List<BookVO>> bookListDO = bookService.selectBookByCriteria(bookListBO);
        DataGridDataSource<BookVO> dataGridDataSource = new DataGridDataSource<>();
        dataGridDataSource.setRows(bookListDO.getData());
        dataGridDataSource.setTotal(bookListDO.getTotal());
        return dataGridDataSource;
    }

    public boolean updateBook(UpdateRequest updateRequest) {
        int i = bookService.updateBook(updateRequest);
        if (i != 1) {
            throw new BizException(ResultCode.BOOK_UPDATE_FAIL);
        }
        return true;
    }

    public BookVO selectBookDetail(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        BookDO bookDO = bookService.selectBookById(bookId);
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        BookVO bookVO = new BookVO();
        BeanUtils.copyProperties(bookDO, bookVO);
        bookVO.setCreateTime(CalendarUtils.dateToString(bookDO.getCreateTime()));
        bookVO.setUpdateTime(CalendarUtils.dateToString(bookDO.getUpdateTime()));
        bookVO.setPublishTime(CalendarUtils.dataToStringDay(bookDO.getPublishTime()));
        return bookVO;
    }


    public DataGridDataSource<BookVO> selectUserBookByCriteria(BookListBO bookListBO) {
        BookListDO<List<BookVO>> bookListDO = bookService.selectUserBookByCriteria(bookListBO);
        DataGridDataSource<BookVO> dataGridDataSource = new DataGridDataSource<>();
        dataGridDataSource.setRows(bookListDO.getData());
        dataGridDataSource.setTotal(bookListDO.getTotal());
        return dataGridDataSource;
    }
}
