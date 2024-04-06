package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 22:24
 */
@Data
public class DeleteRequest {

    @NotBlank(message = "bookId is null")
    private String bookId;


}
