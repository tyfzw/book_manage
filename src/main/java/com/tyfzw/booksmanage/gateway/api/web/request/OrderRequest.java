package com.tyfzw.booksmanage.gateway.api.web.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-15
 * Time: 10:32
 */
@Data
public class OrderRequest {

    /**
     * 书本id
     */
    @NotBlank(message = "bookId is null")
    private String bookId;


}
