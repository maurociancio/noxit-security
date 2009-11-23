package ar.noxit.security.annotations;

import ar.noxit.security.auth.Authenticator;
import ar.noxit.security.auth.authentication.NullAuthenticator;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Authenticate {

    Class<? extends Authenticator> authenticator() default NullAuthenticator.class;
}
