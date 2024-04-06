package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-24
 * Time: 10:37
 */
@Data
public class RegisterBO {

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;
}
