package com.tyfzw.booksmanage.infrastructure.gateway.database.model;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-24
 * Time: 9:52
 */
@Data
public class UserListDO<T> {

    private T data;

    private Long total;

}
