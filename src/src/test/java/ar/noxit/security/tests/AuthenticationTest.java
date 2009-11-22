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
    public interface Service1 {

        int service();
    }

    @Test
    public void authenticateWithANullAuthenticator() {
        Service1 service = security.secure(Service1.class, new Service1() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    @Authenticate(authorizer = FailureAuthorizer.class)
    public interface Service2 {

        void service() throws NotAuthenticatedException;
    }

    @Test(expected = NotAuthenticatedException.class)
    public void authenticateWithAuthenticatorThatFails() throws NotAuthenticatedException {
        Service2 service = security.secure(Service2.class, new Service2() {

            @Override
            public void service() {
                throw new IllegalStateException("Should never be raised");
            }
        });

        service.service();
    }
}
