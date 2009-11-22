package ar.noxit.security.tests;

import ar.noxit.security.Security;
import ar.noxit.security.SecurityImpl;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.annotations.Authenticate;
import ar.noxit.security.annotations.Rol;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import ar.noxit.security.mocks.CreateRolAuthorizer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AuthenticationAndRolTest {

    private Security security;

    @Before
    public void setUp() {
        security = new SecurityImpl(new CGLibInterceptor());
    }

    @Authenticate(authorizer = CreateRolAuthorizer.class)
    public interface Service1 {

        @Rol(roles = {"create"})
        int service();
    }

    @Test
    public void authenticateWithExistingRol() {
        Service1 service = security.secure(Service1.class, new Service1() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    @Authenticate(authorizer = CreateRolAuthorizer.class)
    public interface Service2 {

        @Rol(roles = {"not-create"})
        int service();
    }

    @Test(expected = NotAuthenticatedException.class)
    public void authenticateWithNotExistingRol() {
        Service2 service = security.secure(Service2.class, new Service2() {

            @Override
            public int service() {
                return 1;
            }
        });

        service.service();
    }
}
