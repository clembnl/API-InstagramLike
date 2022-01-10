package com.instagram.api.exception;

public class PostNotExistException extends IllegalArgumentException {
	public PostNotExistException(String msg) {
		super(msg);
	}

}
