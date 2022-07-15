package me.coldrain.ninetyminute.exception;

public class AuthenticationException extends RuntimeException {
    private final ErrorCode errorType;

    public AuthenticationException(ErrorCode errorType) {
        this.errorType = errorType;
    }
}
