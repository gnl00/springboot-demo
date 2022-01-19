package com.boot.core.exception;

/**
 * CustomAssertException
 *
 * @author lgn
 * @date 2021-11-15 17:26
 */

public class CustomAssertException extends RuntimeException {

    public CustomAssertException() {
        super();
    }

    public CustomAssertException(String message) {
        super(message);
    }
}
