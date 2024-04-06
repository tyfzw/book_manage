package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-24
 * Time: 10:37
 */
@Data
public class RegisterRequest {

    /**
     * 用户昵称
     */
    @NotBlank(message = "nickname is null")
    private String nickname;

    /**
     * 用户邮箱
     */
    @NotBlank(message = "email is null")
    private String email;

    /**
     * 用户密码
     */
    @NotBlank(message = "password is null")
    private String password;
}
