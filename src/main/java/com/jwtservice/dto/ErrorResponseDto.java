package com.jwtservice.dto;

import com.jwtservice.constants.ApplicationConstant;

public class ErrorResponseDto<T> implements ResponseDto<T>{

    protected Integer code = ApplicationConstant.HTTP_RESPONSE_ERROR_CODE;

    protected T data;

    protected String message = null;

    protected Integer status = ApplicationConstant.ERROR_STATUS_CODE;

    // constructors

    public ErrorResponseDto() {}

    public ErrorResponseDto(Integer errorCode, String errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }

    public ErrorResponseDto(Integer errorCode, String errorMessage, T data) {
        this.data = data;
        this.code = errorCode;
        this.message = errorMessage;
    }

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public ErrorResponseDto(T data2, String message2) {
        this.data = data2;
        this.message = message2;
    }

    public ErrorResponseDto(T data2, String message2, Integer status) {
        this(data2, message2);
        this.status = status;
    }

    public ErrorResponseDto(Integer status) {
        this.status = status;
    }

// setters and getters

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public void setCode(Integer code) {
        this.code=code;
    }

    @Override
    public void setData(T data) {
        this.data=data;
    }

    @Override
    public void setMessage(String message) {
        this.message=message;
    }

    @Override
    public void setStatus(Integer status) {
        this.status=status;
    }
}
