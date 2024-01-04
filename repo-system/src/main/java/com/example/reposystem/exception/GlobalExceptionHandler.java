package com.example.reposystem.exception;

import com.example.reposystem.basicentity.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*由于使用了Validation依赖
* 每一次发生不符合规范的错误时都会直接报错500
* 这样返回的信息不符合要求，也不太好查看到到底是哪里出现错误
* 因此我们在这里使用全局处理，接收全局异常处理的对象，将其转化为可读（稍微）的错误报告*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){

        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"操作失败");
    }
}
