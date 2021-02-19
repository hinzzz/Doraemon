package com.hinz.jsr303.exception;

import com.hinz.jsr303.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

/**
 * 集中处理所有异常
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.hinz.jsr303.controller")
public class ValidExceptionControllerAdvice {


    @ExceptionHandler(value= {MethodArgumentNotValidException.class})
    public R handleVaildException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        log.error("校验参数：{}",bindingResult.getTarget().toString());
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());

        Map<String,String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    @ExceptionHandler(value= {ConstraintViolationException.class})
    public R handleVaildException(ConstraintViolationException e){
      /*  BindingResult bindingResult = e.getBindingResult();
        log.error("校验参数：{}",bindingResult.getTarget().toString());
        log.error("数据校验出现问题{}，异常类型：{}",e.getMessage(),e.getClass());*/

        Map<String,String> errorMap = new HashMap<>();
        /*bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });*/
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data",errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){

        log.error("错误：",throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(),BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }


}
