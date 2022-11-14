package com.jwtservice.dto;

import com.jwtservice.constants.ApplicationConstant;
import com.jwtservice.model.User;

public class SuccessResponseDto<T> implements ResponseDto<T>{

    protected Integer code = ApplicationConstant.HTTP_RESPONSE_SUCCESS_CODE;

    protected T data;

    protected String message = null;

    protected Integer status =  ApplicationConstant.SUCCESS_STATUS_CODE;

    //constructors

    public SuccessResponseDto() {
        this(null, "Success");
    }

    public SuccessResponseDto(T data) {
        this(data, "Success");
    }

    public SuccessResponseDto(String message) {
        this.message = message;
    }

    public SuccessResponseDto(T data2, String message2) {
        this.data = data2;
        this.message = message2;
    }

    public SuccessResponseDto(T data2, String message2, Integer status) {
        this(data2, message2);
        this.status = status;
    }

    public SuccessResponseDto(Integer status) {
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
        this.code = code;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setStatus(Integer status) {
        this.status = status;
    }
}
