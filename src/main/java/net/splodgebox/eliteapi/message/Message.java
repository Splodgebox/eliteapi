package net.splodgebox.eliteapi.message;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Message {
    String path();
    String defaultMessage();
}