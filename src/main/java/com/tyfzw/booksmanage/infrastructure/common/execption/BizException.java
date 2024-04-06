package com.tyfzw.booksmanage.infrastructure.common.execption;





import com.tyfzw.booksmanage.infrastructure.common.enums.ResultCode;
import lombok.Data;

/**
 * @Author jiguangzhi
 * @Date 2022/2/13
 */
@Data
public class BizException extends RuntimeException {

    private String message;

    private ResultCode resultCode;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
