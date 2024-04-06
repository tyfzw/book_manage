package com.tyfzw.booksmanage.gateway.api.web.response;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-02-20
 * Time: 22:04
 */
@Data
public class PageResponse<T> {
    private T data;
    private Integer currentPage;
    private long count;
    private long pageCount;

    public static <T> PageResponse<T> build(T data, long count) {
        PageResponse<T> response = new PageResponse<>();
        response.setCount(count);
        response.setData(data);
        return response;
    }
}
