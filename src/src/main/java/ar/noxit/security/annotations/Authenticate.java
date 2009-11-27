package ar.noxit.security.annotations;

import ar.noxit.security.auth.Authenticator;
import ar.noxit.security.auth.authentication.NullAuthenticator;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

/**
 * Use this annotation to mark an interface that requires authentication.
 *
 * If you need both authentication and authorization in your interface see Auth annotation.
 *
 * @see Auth
 * @author Mauro Ciancio
 */
@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Authenticate {

    /**
     * Specify which class should do the authentication
     *
     * @return Authenticator
     */
    Class<? extends Authenticator> authenticator() default NullAuthenticator.class;
}
