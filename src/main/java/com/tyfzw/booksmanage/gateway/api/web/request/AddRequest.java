package com.tyfzw.booksmanage.gateway.api.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 20:53
 */
@Data
public class AddRequest {

    /**
     * ISBN
     */
    @NotBlank(message = "isbn is null")
    private String isbn;

    /**
     * 书名
     */
    @NotBlank(message = "name is null")
    private String name;

    /**
     * 出版社
     */
    @NotBlank(message = "publisher is null")
    private String publisher;


    /**
     * 作者
     */
    @NotBlank(message = "write is null")
    private String writer;

    /**
     * 发行时间
     */
    @NotNull(message = "publishTime is null")
    private Date publishTime;

    /**
     * 地址
     */
    @NotBlank(message = "location is null")
    private String location;

    /**
     * 数量
     */
    @Min(value = 1, message = "number must >=1")
    private Integer number;

    private String remarks;

}
