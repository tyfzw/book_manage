package com.tyfzw.booksmanage.gateway.api.web.response;



import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Author jiguangzhi
 * @Date 2022/2/14
 */
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = -85243339352409747L;

    /**
     * 返回操作码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 成功
     */
    private Boolean success = true;
    /**
     * 返回的数据
     */
    private T data;

    public ResponseResult() {
    }

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>(ResultCode.SUCCESS);
        responseResult.setData(data);
        return responseResult;
    }


    public static <T> ResponseResult<T> fail() {
        ResponseResult<T> responseResult = new ResponseResult<>(ResultCode.SYSTEM_ERROR);
        responseResult.setSuccess(false);
        return responseResult;
    }

    public static <T> ResponseResult<T> fail(ResultCode resultCode) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setMsg(resultCode.getMsg());
        responseResult.setCode(resultCode.getCode());
        responseResult.setSuccess(false);
        return responseResult;
    }

    public static <T> ResponseResult<T> fail(ResultCode resultCode, String extMsg) {
        ResponseResult<T> responseResult = new ResponseResult<>();

        responseResult.setMsg(StringUtils.isBlank(extMsg) ? resultCode.getMsg() : resultCode.getMsg() + ", " + extMsg);
        responseResult.setCode(resultCode.getCode());
        responseResult.setSuccess(false);
        return responseResult;
    }

    public ResponseResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
