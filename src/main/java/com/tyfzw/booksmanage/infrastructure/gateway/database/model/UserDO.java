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
 * Date: 2023-05-13
 * Time: 11:18
 */
@Data
@Table(name = "user")
public class UserDO {
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户姓名
     */
    private String userId;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更行时间
     */
    private Date updateTime;

    /**
     * 用户状态active活跃，freeze冻结，forbidden封禁
     */
    private String status;

    /**
     * 备注
     */
    private String demo;

    /**
     * user用户 admin管理员
     */
    private String identity;

}
