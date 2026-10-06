package com.rnr.SMS.exception;
import com.rnr.SMS.payload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleStudentNotFoundException(
            StudentNotFoundException ex,
            HttpServletRequest request) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmailException(
            DuplicateEmailException ex,
            HttpServletRequest request) {

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}