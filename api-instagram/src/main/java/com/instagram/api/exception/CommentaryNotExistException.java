package com.instagram.api.exception;

public class CommentaryNotExistException extends IllegalArgumentException {
    public CommentaryNotExistException(String msg) {
        super(msg);
    }
}
