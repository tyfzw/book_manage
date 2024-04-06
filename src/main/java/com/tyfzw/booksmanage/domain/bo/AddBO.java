package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 20:53
 */
@Data
public class AddBO {

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
     * 地址
     */
    private String location;

    /**
     * 发行时间
     */
    private Date publishTime;

    /**
     * 数量
     */
    private Integer number;

    private String remarks;

}
