package com.tyfzw.booksmanage.infrastructure.gateway.database.dao;

import com.tyfzw.booksmanage.infrastructure.gateway.database.model.BookDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 9:33
 */
@Repository
public interface BooksDAO extends Mapper<BookDO> {

    List<BookDO> selectTotalBooks();

    List<BookDO> selectBooksLikeName(String name);

    List<BookDO> selectBookByCriteria(String name, String isbn, String writer);

    List<BookDO> selectUserBookByCriteria(String name, String isbn, String writer);
}
