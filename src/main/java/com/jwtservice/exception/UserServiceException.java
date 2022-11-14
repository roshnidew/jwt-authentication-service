package com.jwtservice.exception;

import org.springframework.http.HttpStatus;

public class UserServiceException extends RuntimeException {

    private String errorCode;
    private HttpStatus httpStatus = HttpStatus.OK;
    private Exception ex ;
    public UserServiceException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public UserServiceException(String errorCode,HttpStatus httpStatus) {
        super();
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public UserServiceException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public UserServiceException(String errorCode, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    public UserServiceException(String errorCode, HttpStatus httpStatus, Exception ex) {
        super();
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.ex = ex;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }
}
