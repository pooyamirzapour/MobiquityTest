package com.mobiquity.exception;

public class FileFormatException extends IllegalArgumentException {

    public FileFormatException() {
    }

    public FileFormatException(String s) {
        super(s);
    }

    public FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileFormatException(Throwable cause) {
        super(cause);
    }
}
