package com.tyfzw.booksmanage.infrastructure.gateway.database.model;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 18:59
 */
@Data
public class BookListDO<T> {
    private T data;

    private Long total;


}
