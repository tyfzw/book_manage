package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-17
 * Time: 22:36
 */
@Data
public class RemoveRequest {

    @NotBlank(message = "borrowId is null")
    private String borrowId;

}
