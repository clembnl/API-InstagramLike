package com.instagram.api.exception;

public class LikeNotExistException extends IllegalArgumentException {
    public LikeNotExistException(String msg) {
        super(msg);
    }
}
