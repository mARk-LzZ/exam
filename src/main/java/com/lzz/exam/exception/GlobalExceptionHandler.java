package com.lzz.exam.exception;



import com.lzz.exam.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(value=NumberFormatException.class)
    public R numberFormatException(NumberFormatException e){

        return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR , "Wrong number in files , please check your data " + e.getMessage());
    }

    @ExceptionHandler(value=Exception.class)
    public R allExceptionHandler(Exception e)
    {
        log.info("stack trace: {}" , (Object) e.getStackTrace());
        log.info("message: {}" , e.getMessage());
        return R.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        return  R.error(HttpStatus.SC_BAD_REQUEST,e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return  R.error(HttpStatus.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(RRException.class)
    public R handlehandleRRException(RRException e){
        return  R.error(e.getCode() ,  e.getMsg());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public R handleAccessDeniedException(AccessDeniedException e){
        return R.error(HttpStatus.SC_FORBIDDEN , e.getMessage());
    }


}