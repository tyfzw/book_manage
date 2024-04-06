package com.tyfzw.booksmanage.infrastructure.gateway.database.dao;

import com.tyfzw.booksmanage.infrastructure.gateway.database.model.LogDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-22
 * Time: 9:17
 */
@Repository
public interface LogDAO extends Mapper<LogDO> {

}
