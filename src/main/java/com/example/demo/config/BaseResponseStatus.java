package com.example.demo.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request 오류, Response 오류
     */
    // Common
    REQUEST_ERROR(false, BAD_REQUEST.value(), "입력값을 확인해주세요."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),


    // users
    USERS_EMPTY_USER_ID(false, BAD_REQUEST.value(), "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, BAD_REQUEST.value(), "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,BAD_REQUEST.value(),"중복된 이메일입니다."),

    POST_USERS_EMPTY_ID(false, BAD_REQUEST.value(), "아이디를 입력해주세요."),
    POST_USERS_INVALID_ID(false, BAD_REQUEST.value(), "아이디 형식을 확인해주세요."),

    POST_USERS_EMPTY_PASSWORD(false, BAD_REQUEST.value(), "비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD(false, BAD_REQUEST.value(), "비밀번호 형식을 확인해주세요."),

    FAILED_TO_LOGIN(false,HttpStatus.NOT_FOUND.value(),"없는 아이디거나 비밀번호가 틀렸습니다."),


    PRODUCTS_EMPTY_TITLE(false, BAD_REQUEST.value(), "제목을 입력해주세요."),
    PRODUCTS_EMPTY_PRICE(false, BAD_REQUEST.value(), "가격을 입력해주세요."),
    PRODUCTS_EMPTY_CONTENTS(false, BAD_REQUEST.value(), "내용을 입력해주세요."),
    PRODUCTS_EMPTY_CATEGORY(false, BAD_REQUEST.value(), "내용을 입력해주세요."),

    DELETE_INVALID_PRODUCT(false, BAD_REQUEST.value(), "존재하지 않는 글입니다."),
    DELETE_INVALID_WISHLIST(false, BAD_REQUEST.value(), "존재하지 않는 관심 상품입니다."),

    /**
     * 50 : Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USER(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원 탈퇴 실패"),

    //[PATCH] /product
    DELETE_FAIL_PRODUCT(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "게시글 삭제 실패"),
    MODIFY_FAIL_PRICE(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "가격 수정 실패"),

    DELETE_FAIL_WISHLIST(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "관심 목록에서 삭제 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
