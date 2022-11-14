package com.jwtservice.controller;

import com.jwtservice.dto.ResponseDto;
import com.jwtservice.dto.SuccessResponseDto;
import com.jwtservice.model.User;
import com.jwtservice.payloads.request.LoginRequest;
import com.jwtservice.payloads.request.SignUpRequest;
import com.jwtservice.payloads.response.JwtResponse;
import com.jwtservice.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth-service")
public class AuthController {
    @Autowired
    private CustomUserService customUserService;

    @GetMapping("/msg")
    public String msg(){
        return "Hello JwtAuthentication Service";
    }

 /*   @PostMapping("/sign-Up")
    public User registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        return customUserService.registerUser(signUpRequest);
    }*/

    @PostMapping("/sign-Up")
    public ResponseDto registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
      User user = customUserService.registerUser(signUpRequest);
      return new SuccessResponseDto(user);
    }

    @PostMapping("/sign-In")
    public ResponseDto authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        JwtResponse jwtResponse = customUserService.authenticateUser(loginRequest);
        return new SuccessResponseDto(jwtResponse);
    }

   @GetMapping("/verify-token")
    public ResponseDto verifyToken(@RequestParam String username){
        return new SuccessResponseDto(customUserService.verifyToken(username));
    }



  /*  @PostMapping("/sign-Up-new")
    public ResponseEntity<?> registerUsers(@Valid @RequestBody SignUpRequest signUpRequest){
       customUserService.registerUser(signUpRequest);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }*/

}
