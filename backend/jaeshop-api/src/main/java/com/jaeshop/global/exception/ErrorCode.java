package com.jaeshop.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 Bad Request
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 요청 값입니다."),

    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "카테고리를 찾을 수 없습니다."),
    INVALID_CATEGORY_PARENT(HttpStatus.BAD_REQUEST, "유효하지 않은 상위 카테고리입니다."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "이미 사용중인 카테고리명입니다."),

    DUPLICATED_MODEL_CODE(HttpStatus.BAD_REQUEST, "이미 존재하는 모델 코드입니다."),

    // 401 Unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),

    // 403 Forbidden
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

}
