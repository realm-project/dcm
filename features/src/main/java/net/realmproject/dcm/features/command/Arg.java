package net.realmproject.dcm.features.command;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {

    public String value() default "value";

}
