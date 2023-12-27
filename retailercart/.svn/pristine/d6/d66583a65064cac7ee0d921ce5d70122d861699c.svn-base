package com.botree.common.exception;

import com.botree.common.model.ResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;


@Component
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * This method is used to AuthenticationException.
     * @param ex      AuthenticationException
     * @param request HttpServletRequest
     * @return exception
     */
    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationException(
            final HttpClientErrorException.Unauthorized ex, final HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * This method is used to AuthenticationException.
     * @param ex      AuthenticationException
     * @param request HttpServletRequest
     * @return exception
     */
    @ExceptionHandler({CommonExceptionHandler.class})
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ResponseModel> customNotFound(
            final CommonExceptionHandler ex, final HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE.value())
                .body(new ResponseModel(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()));
    }


}
