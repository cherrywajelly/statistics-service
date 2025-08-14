package com.timetoast.statistics_service.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class UnauthorizedException extends RuntimeException{
    private final String message;

    public UnauthorizedException(final String message){
        this.message = message;
    }
}
