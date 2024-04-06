package com.tyfzw.booksmanage.domain.vo;

import lombok.Data;

import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-13
 * Time: 11:15
 */
@Data
public class UserVO {

    /**
     * 用户id
     */
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
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更行时间
     */
    private String updateTime;

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
