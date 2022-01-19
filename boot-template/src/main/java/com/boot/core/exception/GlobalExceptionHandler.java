package com.boot.core.exception;

import com.boot.core.resp.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler
 *
 * @author lgn
 * @date 2021-11-15 17:32
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public BaseResult defaultHandler(Exception e) {
        log.info("---detective error---");
        return new BaseResult(500, "failure", e.getMessage());
    }

}
