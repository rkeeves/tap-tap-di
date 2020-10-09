package com.rkeeves.taptap;

public class NoCtorForInjectionFoundOnClassException extends Exception {

    public NoCtorForInjectionFoundOnClassException(String message) {
        super(message);
    }
}