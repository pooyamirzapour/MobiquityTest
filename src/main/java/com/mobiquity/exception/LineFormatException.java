package com.mobiquity.exception;

public class LineFormatException extends IllegalArgumentException {

    public LineFormatException() {
    }

    public LineFormatException(String s) {
        super(s);
    }

    public LineFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public LineFormatException(Throwable cause) {
        super(cause);
    }
}
