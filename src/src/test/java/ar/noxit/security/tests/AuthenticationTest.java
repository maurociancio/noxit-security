package ar.noxit.security.tests;

import ar.noxit.security.Security;
import ar.noxit.security.mocks.NullAuthorizer;
import ar.noxit.security.SecurityImpl;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.mocks.FailureAuthorizer;
import ar.noxit.security.annotations.Authenticate;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticationTest {

    private Security security;

    @Before
    public void setUp() {
        security = new SecurityImpl(new CGLibInterceptor());
    }

    @Authenticate(authorizer = NullAuthorizer.class)
    public interface ServiceWithNullAuthorizer {

        int service();
    }

    @Test
    public void authenticateWithANullAuthenticator() {
        ServiceWithNullAuthorizer service = security.secure(ServiceWithNullAuthorizer.class, new ServiceWithNullAuthorizer() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    @Authenticate(authorizer = FailureAuthorizer.class)
    public interface ServiceWithFailureAuthorizer {

        void service() throws NotAuthenticatedException;
    }

    @Test(expected = NotAuthenticatedException.class)
    public void authenticateWithAuthenticatorThatFails() throws NotAuthenticatedException {
        ServiceWithFailureAuthorizer service = security.secure(ServiceWithFailureAuthorizer.class, new ServiceWithFailureAuthorizer() {

            @Override
            public void service() {
                throw new IllegalStateException("Should never be raised");
            }
        });

        service.service();
    }
}
