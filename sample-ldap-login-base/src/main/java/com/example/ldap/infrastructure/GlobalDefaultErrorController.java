package com.example.ldap.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultErrorController {

    private static Logger logger = LoggerFactory.getLogger(GlobalDefaultErrorController.class);

    @ExceptionHandler(value = Exception.class)
    public void handleGenericError(HttpServletRequest request, Exception exception) {

        logger.error("Unexpected error.", exception);
    }

}
