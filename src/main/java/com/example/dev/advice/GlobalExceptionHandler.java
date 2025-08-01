package com.example.dev.advice;

import com.example.dev.Exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        // 创建一个结构化的错误信息体
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", new Date());
        errorBody.put("status", HttpStatus.NOT_FOUND.value());
        errorBody.put("error", "Not Found");
        errorBody.put("message", ex.getMessage()); // 从异常对象中获取具体信息
        errorBody.put("path", request.getDescription(false).replace("uri=", ""));

        // 返回一个包含错误信息体和正确 HTTP 状态码的 ResponseEntity
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        // 创建一个结构化的错误信息体
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", new Date());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Not Found");
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField(); // 获取校验失败的字段名
            String errorMessage = error.getDefaultMessage(); // 获取在注解中定义的 message
            errors.put(fieldName, errorMessage);
        });
        errorBody.put("message", errors);
        errorBody.put("path", request.getDescription(false).replace("uri=", ""));

        // 返回一个包含错误信息体和正确 HTTP 状态码的 ResponseEntity
        return new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
    }

    // 你还可以添加处理其他异常的方法
    @ExceptionHandler(Exception.class) // 处理所有其他未被捕获的异常
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", new Date());
        errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put("error", "Internal Server Error");
        errorBody.put("message", "An unexpected error occurred: " + ex.getMessage());
        errorBody.put("path", request.getDescription(false).replace("uri=", ""));

        // 在服务器日志中记录详细的异常信息，但不暴露给客户端
        // log.error("Unhandled exception:", ex);

        return new ResponseEntity<>(errorBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
