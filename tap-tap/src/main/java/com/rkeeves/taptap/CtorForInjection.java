package com.rkeeves.taptap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = ElementType.CONSTRUCTOR)
@Retention(value = RUNTIME)
public @interface CtorForInjection {
}