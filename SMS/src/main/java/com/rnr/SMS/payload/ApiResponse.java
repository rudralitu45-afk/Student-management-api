package com.rnr.SMS.payload;
import lombok.*;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
}