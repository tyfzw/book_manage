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
 * Time: 16:40
 */
@Data
@Table(name = "borrow")
public class BorrowDO {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 借阅id
     */
    private String borrowId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 书本id
     */
    private String bookId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 预约到期时间
     */
    private Date orderTime;

    /**
     * 借阅开始时间
     */
    private Date borrowStartTime;

    /**
     * 借阅结束时间
     */
    private Date borrowEndTime;

    /**
     * 归还时间
     */
    private Date retTime;

    /**
     * 受理accept 借阅borrow
     */
    private String status;
}
