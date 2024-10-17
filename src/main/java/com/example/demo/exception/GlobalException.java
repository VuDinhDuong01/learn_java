package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalException {
    // khi nào api throw ra lỗi RuntimeException thì nó tự chạy vào đây để throw ra lỗi 

    //những exception mà k catch thì mặc định chạy vào đây ví dụ RuntimeException...
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse>  handleResponseEntity(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(400);
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> AppExceptionEntity(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException exception){
            String enumKey= exception.getFieldError().getDefaultMessage();

            ErrorCode errorCode= ErrorCode.INVALID_KEY;
            try{
                errorCode= errorCode.valueOf(enumKey);
            }catch(IllegalArgumentException eIllegalArgumentException){

            }
           
            ApiResponse apiResponse= new ApiResponse();
            apiResponse.setCode(errorCode.getCode());
            apiResponse.setMessage(errorCode.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }
}
