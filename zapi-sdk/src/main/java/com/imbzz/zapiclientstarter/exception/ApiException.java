package com.imbzz.zapiclientstarter.exception;


import java.io.Serializable;

/**
 * @author imbzz
 * @Date 2024/4/29 23:12
 */
public class ApiException extends Exception implements Serializable {

    private static final long serialVersionUID = 2942420535500634982L;
    private int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return this.code;
    }
}
