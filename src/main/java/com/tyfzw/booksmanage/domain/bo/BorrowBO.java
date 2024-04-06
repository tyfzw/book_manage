package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2023-05-23
 * Time: 16:45
 */
@Data
public class BorrowBO {



    private Integer shouldRetDays;


    private String bookId;


    private String userId;



}
