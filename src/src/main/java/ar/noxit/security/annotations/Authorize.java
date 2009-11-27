package ar.noxit.security.annotations;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.auth.authorization.NullAuthorizer;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark an interface that requires authorization.
 *
 * If you need both authentication and authorization in your interface see Auth annotation.
 *
 * @see Auth
 * @author Mauro Ciancio
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Authorize {

    /**
     * Specify which class should do the authorization
     *
     * @return Authorizer
     */
    Class<? extends Authorizer> authorizer() default NullAuthorizer.class;
}
