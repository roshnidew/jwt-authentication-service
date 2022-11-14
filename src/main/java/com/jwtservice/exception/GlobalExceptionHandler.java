package com.jwtservice.exception;

import com.jwtservice.dto.ErrorResponseDto;
import com.jwtservice.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ResponseDto<?>> handle(UserServiceException ex) {
        String errorCode = ex.getErrorCode();
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        HttpStatus httpStatus = ex.getHttpStatus();
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        log.error("Service Exception: | errorCode: {} | errorMessage: {} | httpStatus: {}", errorCode, errorMessage, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String errorCode = "1001";
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
}
   /* @ExceptionHandler(value= UserServiceException.class)
    public ResponseEntity<ResponseDto<?>> handleUserServiceException(UserServiceException ex
            ,HttpStatus httpStatus, String message) {
        String errorCode = ex.getErrorCode();
        String errorMessage = messageSource.getMessage(errorCode, null, null);
//        System.out.println(errorMessage);
//        HttpStatus httpStatus = ex.getHttpStatus();
//        System.out.println(httpStatus);
//        String [] tempCodeString = errorCode.split("_");
//        int newErrorCode = Integer.parseInt(errorCode);
//        int errorMsg = Integer.parseInt(errorMessage);
//       ErrorResponseDto errorResponseDto = new ErrorResponseDto(newErrorCode,errorMessage);
//      return ResponseEntity.status(httpStatus).body(errorResponseDto);

//      ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1],errorMsg));
//      return ResponseEntity.status(httpStatus).body(errorResponseDto);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus.value(), message);
        return ResponseEntity.status(httpStatus).body(errorResponseDto);
    }

    @Override
    public ResponseEntity<Object> handleServletRequestBindingException(
            ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorCode = "1001";
        String errorMessage = messageSource.getMessage(errorCode, null, null);
        String[] tempCodeString = errorCode.split("_");
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(Integer.parseInt(tempCodeString[1]), errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
}
/**
 * private ResponseEntity<Object> buildErrorResponse(Exception exception,
 *                                                       String message,
 *                                                       HttpStatus httpStatus,
 *                                                       WebRequest request) {
 *         ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
 *         if (printStackTrace && isTraceOn(request)) {
 *             errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
 *         }
 *         return ResponseEntity.status(httpStatus).body(errorResponse);
 *     }
 */
