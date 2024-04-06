package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-07-08
 * Time: 15:30
 */
@Data
public class PageBO<T> {

    /**
     * 当前页数
     */
    private Integer pageNum = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 10;

    private long count;

    private long pageCount;

    private T data;


    public void setPageNum(Integer pageNum) {
        if (pageNum == null) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }

    }
}
