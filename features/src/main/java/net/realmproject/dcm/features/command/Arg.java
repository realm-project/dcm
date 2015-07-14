package net.realmproject.dcm.features.command;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Annotation indicating a nmed argument in a {@link CommandMethod}
 * 
 * @author NAS
 *
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Arg {

    public String value() default "value";

}
