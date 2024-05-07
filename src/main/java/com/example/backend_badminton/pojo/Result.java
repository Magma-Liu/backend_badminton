package com.example.backend_badminton.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    private Integer code;// 0-success,1-fail
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(0, "Success", data);
    }

    public static Result<?> success() {
        return new Result<>(0, "success", null);
    }

    public static Result<?> error(String msg) {
        return new Result<>(1, msg, null);
    }
}
