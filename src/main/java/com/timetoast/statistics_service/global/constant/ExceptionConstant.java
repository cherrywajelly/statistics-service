package com.timetoast.statistics_service.global.constant;

public enum ExceptionConstant {
    INVALID_TOKEN_FORMAT("잘못된 형식의 토큰입니다.");

    private String message;

    ExceptionConstant(final String message) {
        this.message = message;
    }

    public String message(){
        return message;
    }

}
