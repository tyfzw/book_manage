package com.tyfzw.booksmanage.infrastructure.gateway.database.dao;

import com.tyfzw.booksmanage.infrastructure.gateway.database.model.UserDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-13
 * Time: 11:31
 */
@Repository
public interface UserDAO extends Mapper<UserDO> {

    List<UserDO> selectTotalUser(String nickname, String email);
}
