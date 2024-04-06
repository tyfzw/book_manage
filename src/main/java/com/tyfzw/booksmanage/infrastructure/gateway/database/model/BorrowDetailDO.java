package com.tyfzw.booksmanage.infrastructure.gateway.database.model;

import lombok.Data;

import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 17:01
 */
@Data
public class BorrowDetailDO {

    /**
     * 主键
     */
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
     * 书本名
     */
    private String name;

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
     * 受理ACCEPT 借阅BORROW  超时ACCEPT_OUT_TIME BORROW_OUT_TIME
     * ALREADY_RET已归还
     */
    private String status;
}
