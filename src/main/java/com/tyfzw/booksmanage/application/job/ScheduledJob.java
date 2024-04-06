package com.tyfzw.booksmanage.application.job;

import com.tyfzw.booksmanage.application.service.BookService;
import com.tyfzw.booksmanage.application.service.BorrowService;
import com.tyfzw.booksmanage.infrastructure.common.enums.BorrowStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import com.tyfzw.booksmanage.infrastructure.common.execption.BizException;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-07-15
 * Time: 21:27
 */
@Component
@Slf4j
public class ScheduledJob {


    @Autowired
    private BorrowService borrowService;

    @Autowired
    private BookService bookService;


    private final Random random = new Random();


    //每10秒扫描一次，随机抽取20条进行检查，查看是否过期
    @Scheduled(cron = "0/10 * * * * ?")
    public void updateBorrow() {
        try {
            int number = borrowService.selectOrderAndBorrowNumber();
            if (number <= 0) {
                throw new IllegalArgumentException("未查询到记录");
            }
            int i = random.nextInt(number);
            List<BorrowDO> borrowDOS = borrowService.selectBorrowLimit(i);
            for (BorrowDO borrowDO : borrowDOS) {
                if (BorrowStatusEnum.BORROW.name().equals(borrowDO.getStatus())) {
                    if (borrowDO.getBorrowEndTime().compareTo(new Date()) <= 0) {
                        int j = borrowService.updateStatus(borrowDO.getBorrowId(), borrowDO.getStatus(), BorrowStatusEnum.BORROW_OUT_TIME.name());
                        if (j != 1) {
                            throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                        }
                        borrowDO.setUpdateTime(new Date());
                        borrowDO.setStatus(BorrowStatusEnum.BORROW_OUT_TIME.name());
                    }
                }

                if (BorrowStatusEnum.ACCEPT.name().equals(borrowDO.getStatus())) {
                    if (borrowDO.getOrderTime().compareTo(new Date()) <= 0) {
                        int k = borrowService.updateStatus(borrowDO.getBorrowId(), borrowDO.getStatus(), BorrowStatusEnum.ACCEPT_OUT_TIME.name());
                        if (k != 1) {
                            throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                        }
                        BookDO bookDO = bookService.selectBookById(borrowDO.getBookId());
                        if (Objects.isNull(bookDO)) {
                            throw new BizException(ResultCode.BOOKS_NOT_FIND);
                        }
                        int j = bookService.updateFlagByBookId(borrowDO.getBookId(), bookDO.getBorrowFlag());
                        if (j != 1) {
                            throw new BizException(ResultCode.STATUS_UPDATE_FAIL);
                        }
                        borrowDO.setUpdateTime(new Date());
                        borrowDO.setStatus(BorrowStatusEnum.ACCEPT_OUT_TIME.name());
                    }

                }
            }
        } catch (BizException e) {
            log.warn("resultCode:{}", e.getResultCode());
        } catch (Exception e) {
            log.error("e:{}", e);
        }
    }



}
