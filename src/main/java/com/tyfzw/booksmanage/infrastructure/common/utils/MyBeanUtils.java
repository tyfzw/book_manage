package com.tyfzw.booksmanage.infrastructure.common.utils;

import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDetailDO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-17
 * Time: 10:35
 */
public class MyBeanUtils {

    public static List<BorrowVO> BorrowDetailDOToBorrowVO(List<BorrowDetailDO> borrowDetailDOS) {
        ArrayList<BorrowVO> borrowVOS = new ArrayList<>();
        if (Objects.isNull(borrowDetailDOS)) {
            return borrowVOS;
        }
        for (BorrowDetailDO borrowDetailDO : borrowDetailDOS) {
            BorrowVO borrowVO = new BorrowVO();
            BeanUtils.copyProperties(borrowDetailDO, borrowVO);
            borrowVO.setCreateTime(CalendarUtils.dateToString(borrowDetailDO.getCreateTime()));
            borrowVO.setUpdateTime(CalendarUtils.dateToString(borrowDetailDO.getUpdateTime()));
            if (!Objects.isNull(borrowDetailDO.getOrderTime())) {
                borrowVO.setOrderTime(CalendarUtils.dateToString(borrowDetailDO.getOrderTime()));
            }
            if (!Objects.isNull(borrowDetailDO.getBorrowEndTime())) {
                borrowVO.setBorrowEndTime(CalendarUtils.dateToString(borrowDetailDO.getBorrowEndTime()));
            }
            if (!Objects.isNull(borrowDetailDO.getBorrowStartTime())) {
                borrowVO.setBorrowStartTime(CalendarUtils.dateToString(borrowDetailDO.getBorrowStartTime()));
            }
            if (!Objects.isNull(borrowDetailDO.getRetTime())) {
                borrowVO.setRetTime(CalendarUtils.dateToString(borrowDetailDO.getRetTime()));
            }
            borrowVOS.add(borrowVO);
        }
        return borrowVOS;
    }


    public static void main(String[] args) {
        BorrowDetailDOToBorrowVO(null);
    }
}
