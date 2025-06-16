package com.job_connect.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException() {
        super();
    }
    public ForbiddenException(String msg) {
        super(msg);
    }
}
