package com.crypto_tools.exchangapi;

public class Exception extends RuntimeException {
    private static final long serialVersionUID = 3788669840036201041L;
    private Error error;

    public Exception(Error error) {
        this.error = error;
    }

    public Exception() {
    }

    public Exception(String message) {
        super(message);
    }

    public Exception(Throwable cause) {
        super(cause);
    }

    public Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public Error getError() {
        return this.error;
    }

    public String getMessage() {
        return this.error != null ? this.error.getMsg() : super.getMessage();
    }
}
