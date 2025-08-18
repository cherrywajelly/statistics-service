package com.timetoast.statistics_service.global.constant;

public enum RedisKeyConstant {
    MONTH_SIGNUP("month-signup"),
    SIGN_UP("signup"),
    DAY("day"),

    DASH("-"),
    COLON(":");

    private final String value;

    RedisKeyConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
