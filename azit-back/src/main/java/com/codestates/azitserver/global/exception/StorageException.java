package com.codestates.azitserver.global.exception;

// TODO : Storage excrption은 현재 500번대 에러를 출력합니다.
public class StorageException extends RuntimeException {
	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
