package com.jaeshop.global.response;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final String message;
    private final String errorCode; // ✅ 에러코드 필드 추가 (null이면 OK)

    private ApiResponse(boolean success, T data, String message, String errorCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    // ✅ 성공 (데이터 있을 때)
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    // ✅ 성공 (빈 응답)
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(true, null, null, null);
    }

    // ✅ 실패 (메시지만)
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, null, message, null);
    }

    // ✅ 실패 (메시지 + 에러코드 — 확장용)
    public static <T> ApiResponse<T> fail(String message, String errorCode) {
        return new ApiResponse<>(false, null, message, errorCode);
    }
}
