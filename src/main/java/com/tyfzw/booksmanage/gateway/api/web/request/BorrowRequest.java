package com.tyfzw.booksmanage.gateway.api.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 16:45
 */
@Data
public class BorrowRequest {


    @Min(value = 1, message = "shouldRetDays must >=1")
    private Integer shouldRetDays;

    @NotBlank(message = "bookId is null")
    private String bookId;

    @NotBlank(message = "bookId is null")
    private String userId;



}
