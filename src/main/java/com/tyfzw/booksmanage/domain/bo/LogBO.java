package com.tyfzw.booksmanage.domain.bo;

import lombok.Data;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: Tyfzw
 * Date: 2022-05-22
 * Time: 15:36
 */
@Data
public class LogBO {
    /**
     * 主键
     */
    private Long id;


    /**
     * 系统名称
     */
    private String systemName;

    /**
     * 系统调用时间
     */
    private Long systemCallTime;


    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 错误信息
     */
    private String errorMessage;


    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 接口入参
     */
    private String interfaceInput;


    /**
     * 接口出参
     */
    private String interfaceOutput;



}
