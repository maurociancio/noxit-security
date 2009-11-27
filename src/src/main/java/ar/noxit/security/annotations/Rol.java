package ar.noxit.security.annotations;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark a method or a class that needs roles in order
 * to be executed.
 *
 * @author Mauro Ciancio
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Rol {

    /**
     * Roles required to execute the service.
     * @return String[]
     */
    String[] roles() default {};
}
