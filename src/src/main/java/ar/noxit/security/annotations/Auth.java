package ar.noxit.security.annotations;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

/**
 * Using this annotation can mark an interface (or a method) that requires both authorization
 * and authentication.
 *
 * @author Mauro Ciancio
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Auth {

    /**
     * Authorize annotation. This annotation specifies what class should implement the
     * authorization.
     *
     * @return Authorize
     */
    Authorize authorize();

    /**
     * Authenticate annotation. This annotation specifies what class should implement the
     * authentication.
     *
     * @return Authorize
     */
    Authenticate authenticate();
}
