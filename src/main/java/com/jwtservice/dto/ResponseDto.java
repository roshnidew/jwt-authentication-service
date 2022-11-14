package com.jwtservice.dto;
public interface ResponseDto<T> {

    Integer getCode();

    T getData();

    String getMessage();

    Integer getStatus();

    void setCode(Integer code);

    void setData(T data);

    void setMessage(String message);

    void setStatus(Integer status);

}