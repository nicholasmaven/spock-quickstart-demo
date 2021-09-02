package com.github.nicholasmaven.spock.demo.exception;

/**
 * @author mawen
 */
public class UserException extends RuntimeException {
    public UserException(String msg) {
        super(msg);
    }
}
