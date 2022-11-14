package com.jwtservice.service;

import com.jwtservice.dto.ResponseDto;
import com.jwtservice.model.User;
import com.jwtservice.payloads.request.LoginRequest;
import com.jwtservice.payloads.request.SignUpRequest;
import com.jwtservice.payloads.response.JwtResponse;


public interface CustomUserService {
    public User registerUser(SignUpRequest signUpRequest);

   JwtResponse authenticateUser(LoginRequest loginRequest);

    //  public void registerUsers(SignUpRequest request);

    public ResponseDto verifyToken(String username);

}
