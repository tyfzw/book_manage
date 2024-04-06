package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-24
 * Time: 11:19
 */
@Data
public class UpdateUserRequest {


    /**
     * 用户id
     */
    @NotBlank(message = "userId is null")
    private String userId;



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

}
