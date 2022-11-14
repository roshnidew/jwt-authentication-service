package com.jwtservice.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.jwtservice.constants.ApplicationConstant;
import com.jwtservice.dto.ErrorResponseDto;
import com.jwtservice.dto.ResponseDto;
import com.jwtservice.dto.SuccessResponseDto;
import com.jwtservice.exception.UserServiceException;
import com.jwtservice.model.User;
import com.jwtservice.payloads.request.LoginRequest;
import com.jwtservice.payloads.request.SignUpRequest;
import com.jwtservice.payloads.response.JwtResponse;
import com.jwtservice.repository.UserRepository;
import com.jwtservice.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomUserServiceImpl implements CustomUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public User registerUser(SignUpRequest signUpRequest) {
        Boolean userName = userRepository.existsByUsername(signUpRequest.getUsername());

        System.out.println("********************************************************"+userName);
        Boolean userEmail = userRepository.existsByEmail(signUpRequest.getEmail());
        System.out.println("********************************************************"+userEmail);
        if(userName==true)
           throw new UserServiceException("SAS_1001");
           // throw new UserServiceException("UserName already exists");
        if(userEmail==true)
            throw new UserServiceException("SAS_1002");
         User user = new User();
            user.setUsername(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
          return userRepository.save(user);

    }

    @Override
    public JwtResponse authenticateUser(LoginRequest loginRequest) {

           Authentication authentication = authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
           //  System.out.println(authentication.toString());

           SecurityContextHolder.getContext().setAuthentication(authentication);

           String jwt = jwtUtils.generateJwtToken(authentication);

           UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
           redisTemplate.opsForValue().set(userDetails.getUsername(), jwt);

           System.out.println("********************************************************");

           log.info("token has been generated and saved in cache for user {}", userDetails.getUsername());
           return new JwtResponse(jwt,
                   userDetails.getId(),
                   userDetails.getUsername(),
                   userDetails.getEmail());
    }

    @Override
    public ResponseDto verifyToken(String username) {
     //   String token = jwtUtils.getUserNameFromJwtToken(username);
        String token = redisTemplate.opsForValue().get(username);
       if(isEmptyOrNull(token)) {
           boolean result = jwtUtils.validateJwtToken(token);
           if (result)
               return new SuccessResponseDto(ApplicationConstant.HTTP_RESPONSE_SUCCESS_CODE, "token is valid");
       }
           return new ErrorResponseDto(ApplicationConstant.HTTP_RESPONSE_UNAUTHORIZED_CODE,"Unauthorized");

    }

    private Boolean isEmptyOrNull(String value){
        boolean flag = false;
        if(value == null) flag = true;
        else if(value.trim().isEmpty()) flag = true;
        return flag;

    }

 /*   @Override
    public void registerUsers(SignUpRequest request) {
        if(userRepository.existsByUsername(request.getUsername()) || userRepository.existsByEmail(request.getEmail()))
            throw new UserServiceException("User exists by email or username");

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }
*/

}
