package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 20:56
 */
@Data
public class UpdateRequest {


    private String bookId;

    /**
     * ISBN
     */

    private String isbn;

    /**
     * 书名
     */

    private String name;

    /**
     * 出版社
     */

    private String publisher;


    /**
     * 作者
     */

    private String writer;

    /**
     * 发行时间
     */

    private String publishTime;

    /**
     * 地址
     */

    private String location;


    private String remarks;

}
