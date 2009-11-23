package ar.noxit.security.annotations;

import ar.noxit.security.auth.Authenticator;
import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.auth.authentication.NullAuthenticator;
import ar.noxit.security.auth.authorization.NullAuthorizer;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Auth {

    Class<? extends Authorizer> authorizer() default NullAuthorizer.class;

    Class<? extends Authenticator> authenticator() default NullAuthenticator.class;
}