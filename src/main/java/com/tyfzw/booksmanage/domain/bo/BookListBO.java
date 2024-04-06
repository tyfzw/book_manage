package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 18:43
 */
@Data
public class BookListBO {

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 书名
     */
    private String name;


    /**
     * 作者
     */
    private String writer;

    /**
     * 页数
     */
    private Integer page;


    /**
     * 每页几行
     */
    private Integer rows;
}
