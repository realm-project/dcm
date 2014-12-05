package net.realmproject.dcm.command;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 
 * Indicates that a method in a {@link CommandDevice} is meant to process
 * {@link Command}s from received events. Optionally specifies a replacement
 * method name, as well.
 * 
 * @author NAS
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandMethod {

    public String value() default "";
}
