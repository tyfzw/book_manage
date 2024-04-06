package com.tyfzw.booksmanage.application.handler;

import com.tyfzw.booksmanage.application.service.BookService;
import com.tyfzw.booksmanage.application.service.BorrowService;
import com.tyfzw.booksmanage.domain.bo.AdminOrderBO;
import com.tyfzw.booksmanage.domain.bo.BorrowBO;
import com.tyfzw.booksmanage.domain.bo.RemoveBO;
import com.tyfzw.booksmanage.domain.bo.RetBO;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.gateway.api.web.response.BookResponse;
import com.tyfzw.booksmanage.infrastructure.common.enums.BookStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.BorrowStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import com.tyfzw.booksmanage.infrastructure.common.utils.MyBeanUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDO;
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
 * Time: 18:45
 */
@Service
public class BorrowHandler {

    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;


    public List<BorrowVO> borrowDetail(String userId) {
        List<BorrowDetailDO> borrowDetailDOS = borrowService.selectBorrowByUserId(userId);
        for (BorrowDetailDO borrowDetailDO : borrowDetailDOS) {
            if (BorrowStatusEnum.BORROW.name().equals(borrowDetailDO.getStatus())) {
                if (Objects.isNull(borrowDetailDO.getBorrowEndTime())) {
                    throw new BizException(ResultCode.OUT_TIME_NOT_FIND);
                }
                if (borrowDetailDO.getBorrowEndTime().compareTo(new Date()) <= 0) {
                    int i = borrowService.updateStatus(borrowDetailDO.getBorrowId(),borrowDetailDO.getStatus(), BorrowStatusEnum.BORROW_OUT_TIME.name());
                    if (i != 1) {
                        throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                    }
                    borrowDetailDO.setUpdateTime(new Date());
                    borrowDetailDO.setStatus(BorrowStatusEnum.BORROW_OUT_TIME.name());
                }
            }
        }
        return MyBeanUtils.BorrowDetailDOToBorrowVO(borrowDetailDOS);
    }

    @Transactional
    public boolean returnBook(RetBO retBO) {
        BorrowDO borrowDO = borrowService.selectBorrowByBorrowId(retBO.getBorrowId());
        if (Objects.isNull(borrowDO)) {
            throw new BizException(ResultCode.BORROW_INFO_NOT_FIND);
        }
        if (BorrowStatusEnum.ALREADY_RET.name().equals(borrowDO.getStatus())) {
            throw new BizException(ResultCode.BOOK_ALREADY_RET);
        }
        int i = borrowService.returnBook(retBO.getBorrowId(), borrowDO.getStatus());
        if (i != 1) {
            throw new BizException(ResultCode.BOOK_RET_FAIL);
        }
        BookDO bookDO = bookService.selectBookById(borrowDO.getBookId());
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        int j = bookService.updateFlagByBookId(borrowDO.getBookId(), bookDO.getBorrowFlag());
        if (j != 1) {
            throw new BizException(ResultCode.BOOK_RET_FAIL);
        }
        return true;
    }

    public List<BorrowVO> getOrderDetail() {
        List<BorrowDetailDO> borrowDetailDOS = borrowService.selectOrderDetail();
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
                    borrowDetailDO.setUpdateTime(new Date());
                    borrowDetailDO.setStatus(BorrowStatusEnum.ACCEPT_OUT_TIME.name());
                }

            }
        }
        return MyBeanUtils.BorrowDetailDOToBorrowVO(borrowDetailDOS);
    }

    @Transactional
    public boolean agreeOrder(AdminOrderBO adminOrderBO) {
        BorrowDO borrowDO = borrowService.selectBorrowByBorrowId(adminOrderBO.getBorrowId());
        if (Objects.isNull(borrowDO)) {
            throw new BizException(ResultCode.BORROW_INFO_NOT_FIND);
        }
        if (BorrowStatusEnum.ACCEPT_OUT_TIME.name().equals(borrowDO.getStatus())) {
            throw new BizException(ResultCode.ORDER_OUT_TIME);
        }
        if (BorrowStatusEnum.BORROW.name().equals(borrowDO.getStatus())) {
            throw new BizException(ResultCode.BOOK_ALREADY_BORROW);
        }
        if (BorrowStatusEnum.DELETE.name().equals(borrowDO.getStatus())) {
            throw new BizException(ResultCode.BOOK_ALREADY_DELETE);
        }
        BookDO bookDO = bookService.selectBookById(borrowDO.getBookId());
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        if (BookStatusEnum.DELETE.name().equals(bookDO.getStatus())) {
            int i = borrowService.deleteBorrowByBorrowId(borrowDO.getBorrowId(), borrowDO.getStatus());
            if (i != 1) {
                throw new BizException(ResultCode.BORROW_DELETE_FAIL);
            }
            throw new BizException(ResultCode.BOOK_ALREADY_DELETE);
        }
        int j = borrowService.borrowBookByBorrowId(adminOrderBO.getBorrowId(), borrowDO.getStatus());
        if (j != 1) {
            throw new BizException(ResultCode.BOOKS_BORROW_FAIL);
        }
        return true;
    }

    public List<BorrowVO> adminBorrowDetail() {
        List<BorrowDetailDO> borrowDetailDOS = borrowService.selectBorrow();
        for (BorrowDetailDO borrowDetailDO : borrowDetailDOS) {
            if (BorrowStatusEnum.BORROW.name().equals(borrowDetailDO.getStatus())) {
                if (Objects.isNull(borrowDetailDO.getBorrowEndTime())) {
                    throw new BizException(ResultCode.OUT_TIME_NOT_FIND);
                }
                if (borrowDetailDO.getBorrowEndTime().compareTo(new Date()) <= 0) {
                    int i = borrowService.updateStatus(borrowDetailDO.getBorrowId(),borrowDetailDO.getStatus(), BorrowStatusEnum.BORROW_OUT_TIME.name());
                    if (i != 1) {
                        throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                    }
                    borrowDetailDO.setUpdateTime(new Date());
                    borrowDetailDO.setStatus(BorrowStatusEnum.BORROW_OUT_TIME.name());
                }
            }
        }
        return MyBeanUtils.BorrowDetailDOToBorrowVO(borrowDetailDOS);
    }

    public boolean removeBorrow(RemoveBO removeBO) {
        BorrowDO borrowDO = borrowService.selectBorrowByBorrowId(removeBO.getBorrowId());
        if (Objects.isNull(borrowDO)) {
            throw new BizException(ResultCode.BORROW_INFO_NOT_FIND);
        }
        int i = borrowService.removeBorrowByBorrowId(borrowDO.getBorrowId(), borrowDO.getStatus());
        if (i != 1) {
            throw new BizException(ResultCode.CLEAR_BORROW_FAIL);
        }
        return true;
    }

    public BookResponse bookInfo(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        BookDO bookDO = bookService.selectBookById(bookId);
        if (Objects.isNull(bookDO)) {
            throw new BizException(ResultCode.BOOKS_NOT_FIND);
        }
        BookResponse bookResponse = new BookResponse();
        BeanUtils.copyProperties(bookDO, bookResponse);
        return bookResponse;
    }

    @Transactional
    public boolean borrowBook(BorrowBO borrowBO) {
        BookDO bookDO = bookService.selectBookById(borrowBO.getBookId());
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

        int j = bookService.order(borrowBO.getBookId());
        if (j != 1) {
            throw new BizException(ResultCode.BOOKS_BORROW_FAIL);
        }
        int i = borrowService.insertBorrow(borrowBO.getBookId(), borrowBO.getShouldRetDays(), borrowBO.getUserId());
        if (i != 1) {
            throw new BizException(ResultCode.BOOKS_BORROW_FAIL);
        }
        return true;
    }

}
