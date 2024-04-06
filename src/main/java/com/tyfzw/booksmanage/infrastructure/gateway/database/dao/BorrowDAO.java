package com.tyfzw.booksmanage.infrastructure.gateway.database.dao;

import com.tyfzw.booksmanage.domain.vo.BorrowVO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDO;
import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BorrowDetailDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 17:32
 */
@Repository
public interface BorrowDAO extends Mapper<BorrowDO> {

    List<BorrowDetailDO> selectOrderByUserId(String userId);

    List<BorrowDetailDO> selectBorrowByUserId(String userId);

    List<BorrowDetailDO> selectOrderDetail();

    List<BorrowDetailDO> selectBorrow();

    List<BorrowDO> selectBorrowLimit(int i);
}
