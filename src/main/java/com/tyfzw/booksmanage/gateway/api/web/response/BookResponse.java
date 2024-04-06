package com.tyfzw.booksmanage.gateway.api.web.response;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 16:30
 */
@Data
public class BookResponse {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 书本id
     */
    private String bookId;

    /**
     * 书本名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更行时间
     */
    private Date updateTime;

    /**
     * 发行方
     */
    private String publisher;

    /**
     * 作者
     */
    private String writer;

    /**
     * 发行时间
     */
    private Date publishTime;

    /**
     * 国际标准书号
     */
    private String isbn;

    /**
     * 位置
     */
    private String location;

    /**
     * 状态
     */
    private String status;

    /**
     * 借阅标记（yes表示已借阅，no表示未借阅）
     */
    private String borrowFlag;
}
