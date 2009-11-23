package ar.noxit.security.annotations;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.auth.authorization.NullAuthorizer;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Authorize {

    Class<? extends Authorizer> authorizer() default NullAuthorizer.class;
}
