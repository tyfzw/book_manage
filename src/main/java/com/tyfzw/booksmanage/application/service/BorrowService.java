package com.tyfzw.booksmanage.application.service;

import cn.hutool.core.util.IdUtil;
import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.infrastructure.common.enums.BorrowStatusEnum;
import com.tyfzw.booksmanage.infrastructure.common.utils.CalendarUtils;
import com.tyfzw.booksmanage.infrastructure.gateway.database.dao.BorrowDAO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDetailDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.management.BadBinaryOpValueExpException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 17:56
 */
@Service
public class BorrowService {

    @Autowired
    private BorrowDAO borrowDAO;


    public List<BorrowDetailDO> selectOrderByUserId(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }

        return borrowDAO.selectOrderByUserId(userId);
    }

    public void insertBorrow(String bookId, String userId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setBorrowId(Long.toString(IdUtil.getSnowflake().nextId()));
        borrowDO.setUserId(userId);
        borrowDO.setBookId(bookId);
        borrowDO.setCreateTime(new Date());
        borrowDO.setUpdateTime(new Date());
        borrowDO.setOrderTime(CalendarUtils.TimeAddDay(5, new Date()));
        borrowDO.setStatus("ACCEPT");
        borrowDAO.insert(borrowDO);
    }

    public List<BorrowDetailDO> selectBorrowByUserId(String userId) {
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        return borrowDAO.selectBorrowByUserId(userId);
    }

    public BorrowDO selectBorrowByBorrowId(String borrowId) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria().andEqualTo("borrowId", borrowId);
        return borrowDAO.selectOneByExample(example);
    }

    public int returnBook(String borrowId, String status) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        if (Strings.isBlank(status)) {
            throw new IllegalArgumentException("status is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .andEqualTo("borrowId", borrowId)
                .andEqualTo("status", status);
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setStatus(BorrowStatusEnum.ALREADY_RET.name());
        borrowDO.setUpdateTime(new Date());
        borrowDO.setRetTime(new Date());
        return borrowDAO.updateByExampleSelective(borrowDO, example);
    }

    public List<BorrowDetailDO> selectOrderDetail() {
        return borrowDAO.selectOrderDetail();
    }

    public int deleteBorrowByBorrowId(String borrowId, String status) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        if (Strings.isBlank(status)) {
            throw new IllegalArgumentException("status is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .andEqualTo("borrowId", borrowId)
                .andEqualTo("status", status);
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setUpdateTime(new Date());
        borrowDO.setStatus(BorrowStatusEnum.DELETE.name());
        return borrowDAO.updateByExampleSelective(borrowDO, example);
    }

    public int borrowBookByBorrowId(String borrowId, String status) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        if (Strings.isBlank(status)) {
            throw new IllegalArgumentException("status is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .andEqualTo("borrowId", borrowId)
                .andEqualTo("status", status);
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setUpdateTime(new Date());
        borrowDO.setBorrowStartTime(new Date());
        borrowDO.setBorrowEndTime(CalendarUtils.TimeAddDay(5, new Date()));
        borrowDO.setStatus(BorrowStatusEnum.BORROW.name());
        return borrowDAO.updateByExampleSelective(borrowDO, example);
    }

    public List<BorrowDetailDO> selectBorrow() {
        return borrowDAO.selectBorrow();
    }

    public BorrowDO selectBorrowByBookId(String bookId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria().andEqualTo("bookId", bookId);
        return borrowDAO.selectOneByExample(example);
    }

    public int removeBorrowByBorrowId(String borrowId, String status) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        if (Strings.isBlank(status)) {
            throw new IllegalArgumentException("status is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .andEqualTo("borrowId", borrowId)
                .andEqualTo("status", status);
        return borrowDAO.deleteByExample(example);
    }

    public int updateStatus(String borrowId, String oldStatus, String newStatus) {
        if (Strings.isBlank(borrowId)) {
            throw new IllegalArgumentException("borrowId is null");
        }
        if (Strings.isBlank(oldStatus)) {
            throw new IllegalArgumentException("oldStatus is null");
        }
        if (Strings.isBlank(newStatus)) {
            throw new IllegalArgumentException("newStatus is null");
        }
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .andEqualTo("borrowId", borrowId)
                .andEqualTo("status", oldStatus);
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setBorrowId(borrowId);
        borrowDO.setStatus(newStatus);
        borrowDO.setUpdateTime(new Date());
        return borrowDAO.updateByExampleSelective(borrowDO, example);
    }

    public int selectOrderAndBorrowNumber() {
        Example example = new Example(BorrowDO.class);
        example.createCriteria()
                .orEqualTo("status", BorrowStatusEnum.BORROW.name())
                .orEqualTo("status", BorrowStatusEnum.ACCEPT.name());

        return borrowDAO.selectCountByExample(example);
    }

    public List<BorrowDO> selectBorrowLimit(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("限制数目小于0");
        }
        return borrowDAO.selectBorrowLimit(i);
    }


    public int insertBorrow(String bookId, Integer shouldRetDays, String userId) {
        if (Strings.isBlank(bookId)) {
            throw new IllegalArgumentException("bookId is null");
        }
        if (Objects.isNull(shouldRetDays)) {
            throw new IllegalArgumentException("shouldRetDays is null");
        }
        if (Strings.isBlank(userId)) {
            throw new IllegalArgumentException("userId is null");
        }
        BorrowDO borrowDO = new BorrowDO();
        borrowDO.setBorrowId(Long.toString(IdUtil.getSnowflake().nextId()));
        borrowDO.setBookId(bookId);
        borrowDO.setUserId(userId);
        borrowDO.setCreateTime(new Date());
        borrowDO.setUpdateTime(new Date());
        borrowDO.setBorrowStartTime(new Date());
        borrowDO.setBorrowEndTime(CalendarUtils.TimeAddDay(shouldRetDays, new Date()));
        borrowDO.setStatus(BorrowStatusEnum.BORROW.name());
        return borrowDAO.insert(borrowDO);
    }

}
