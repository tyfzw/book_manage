package com.tyfzw.booksmanage.infrastructure.gateway.database.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 9:28
 */
@Data
@Table(name = "book")
public class BookDO {

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    private String remarks;
}
