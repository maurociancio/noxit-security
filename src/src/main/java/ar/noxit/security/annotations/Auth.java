package ar.noxit.security.annotations;

import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Auth {

    Authorize authorize();

    Authenticate authenticate();
}
