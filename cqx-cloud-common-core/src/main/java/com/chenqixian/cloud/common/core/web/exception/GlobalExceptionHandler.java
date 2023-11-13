package com.chenqixian.cloud.common.core.web.exception;

import com.chenqixian.cloud.common.core.web.domain.ErrorDetail;
import com.chenqixian.cloud.common.core.web.domain.ErrorStack;
import com.chenqixian.cloud.common.core.web.domain.GlobalError;
import com.chenqixian.cloud.common.core.web.domain.Result;
import com.chenqixian.cloud.common.core.web.util.ResultUtils;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Result> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.METHOD_NOT_ALLOWED, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Result> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        Throwable throwable = e.getCause();
        List<ErrorDetail> errorDetails = new ArrayList();
        if (InvalidFormatException.class.isInstance(throwable)) {
            InvalidFormatException invalidFormatException = (InvalidFormatException)throwable;
            errorDetails.add(new ErrorDetail(((JsonMappingException.Reference)invalidFormatException.getPath().get(0)).getFieldName(), invalidFormatException.getValue(), invalidFormatException.getOriginalMessage()));
        }

        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.METHOD_NOT_ALLOWED, errorDetails, errorStack);
        return new ResponseEntity(result, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Result> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.UNPROCESABLE_ENTITY, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<Result> handleNumberFormatException(NumberFormatException e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.UNPROCESABLE_ENTITY, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Result> handleException(IllegalArgumentException e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.UNPROCESABLE_ENTITY, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Result> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        List<ErrorDetail> errorDetails = new ArrayList();
        Iterator var3 = e.getBindingResult().getFieldErrors().iterator();

        while(var3.hasNext()) {
            FieldError error = (FieldError)var3.next();
            errorDetails.add(new ErrorDetail(error.getField(), error.getRejectedValue(), error.getDefaultMessage()));
        }

        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.UNPROCESABLE_ENTITY, errorDetails, errorStack);
        return new ResponseEntity(result, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Result> handleServiceException(BusinessException e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(e.getCode(), e.getMessage(), e.getErrorType(), e.getErrorDetails(), errorStack);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Result> handleException(Exception e) {
        e.printStackTrace();
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.INTERNAL_SERVER_ERROR, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @ExceptionHandler({PreAuthorizeException.class})
    public ResponseEntity preAuthorizeException(PreAuthorizeException e) {
        ErrorStack errorStack = new ErrorStack(e.getMessage(), getStackTrace(e));
        Result result = ResultUtils.fail(GlobalError.INTERNAL_SERVER_ERROR, (List)null, errorStack);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        String var3;
        try {
            throwable.printStackTrace(pw);
            var3 = sw.toString();
        } finally {
            pw.close();
        }

        return var3;
    }
}
