package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.demo.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalException {
    // khi nào api throw ra lỗi RuntimeException thì nó tự chạy vào đây để throw 
    // những exception mà k catch thì mặc định chạy vào đây ví dụ
    // RuntimeException...
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleResponseEntity(Exception exception) {
        // ApiResponse apiResponse = new ApiResponse();
        ApiResponse apiResponse = ApiResponse.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage()).build();
        // apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        // apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }


    // lỗi trả về runtimeException
    @ExceptionHandler(AppException.class)
    ResponseEntity<ApiResponse> AppExceptionEntity(AppException exception) {
        // System.out.println("lỗi đây:" + exception.getErrorCode());
        // Enum trong Java, khi in ra (ví dụ khi bạn gọi System.out.println() hoặc log
        // nó),
        // sẽ tự động in ra tên của enum constant, không phải các giá trị của các thuộc
        // tính bên trong nó (như code hay message).
        // sẽ in ra gì mà bạn throw
        // Vì thế, khi bạn log exception.getErrorCode(), kết quả sẽ là tên của enum (ví
        // dụ: USER_EXISTED), chứ không phải giá trị code hay message.
        // Nhưng bạn vẫn có thể sử dụng exception.getErrorCode().getCode()
        // Lý do bạn vẫn có thể gọi exception.getErrorCode().getCode() sau đó là vì
        // exception.getErrorCode()
        // trả về đối tượng của kiểu ErrorCode. Và trong enum ErrorCode,
        // bạn đã định nghĩa phương thức getCode() để lấy giá trị của thuộc tính code từ
        // đối tượng đó.

        // nó trả về ErrorCode.USER_EXISTED
        ErrorCode errorCode = exception.getErrorCode();
        // ApiResponse apiResponse = new ApiResponse();
        ApiResponse apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage())
                .build();
        // apiResponse.setCode(errorCode.getCode());
        // apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }
    
    // lỗi liên quan tới validate dữ liệu request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = errorCode.valueOf(enumKey);
        } catch (IllegalArgumentException eIllegalArgumentException) {

        }
        // ApiResponse apiResponse = new ApiResponse();
        ApiResponse apiResponse = ApiResponse.builder().code(errorCode.getCode()).message(errorCode.getMessage())
                .build();
        // apiResponse.setCode(errorCode.getCode());
        // apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }


    // khi có lỗi là access denied
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ApiResponse> handleAccessDenied(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        return ResponseEntity.status(errorCode.getStatusCode()).body(ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
    }


    // vì 401 là sử lý lỗi ở phần filter trước khi vào controller nên sử lý ở phần security config
}
