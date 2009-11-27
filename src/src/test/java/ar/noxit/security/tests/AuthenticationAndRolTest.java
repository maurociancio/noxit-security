package ar.noxit.security.tests;

import ar.noxit.security.Security;
import ar.noxit.security.SecurityImpl;
import ar.noxit.security.annotations.Authenticate;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.annotations.Authorize;
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

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    public interface ServiceWithCreateRol {

        @Rol(roles = {"create"})
        int service();
    }

    @Test
    public void authenticateWithExistingRol() {
        ServiceWithCreateRol service = security.secure(ServiceWithCreateRol.class, new ServiceWithCreateRol() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    public interface ServiceWithNotCreateRol {

        @Rol(roles = {"not-create"})
        int service();
    }

    @Test(expected = NotAuthenticatedException.class)
    public void authenticateWithNotExistingRol() {
        ServiceWithNotCreateRol service = security.secure(ServiceWithNotCreateRol.class, new ServiceWithNotCreateRol() {

            @Override
            public int service() {
                return 1;
            }
        });

        service.service();
    }

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    public interface ServiceWithoutRol {

        int service();
    }

    @Test
    public void authenticateWithoutRol() {
        ServiceWithoutRol service = security.secure(ServiceWithoutRol.class, new ServiceWithoutRol() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    @Rol(roles = "create")
    public interface ServiceWithClassRol {

        int service();
    }

    @Test
    public void authenticateWithClassRoles() {
        ServiceWithClassRol service = security.secure(ServiceWithClassRol.class, new ServiceWithClassRol() {

            @Override
            public int service() {
                return 1;
            }
        });

        assertEquals(service.service(), 1);
    }

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    @Rol(roles = "create")
    public interface ServiceWithClassAndMethodRoles {

        @Rol(roles = "create")
        int service();
    }

    @Test
    public void authenticateWithClassAndMethodRoles() {
        ServiceWithClassAndMethodRoles service = security.secure(ServiceWithClassAndMethodRoles.class,
                new ServiceWithClassAndMethodRoles() {

                    @Override
                    public int service() {
                        return 1;
                    }
                });

        assertEquals(service.service(), 1);
    }

    /********************************************************************************************************/
    @Authenticate
    @Authorize(authorizer = CreateRolAuthorizer.class)
    @Rol(roles = "create")
    public interface ServiceWithClassAndMethodThatNotMatch {

        @Rol(roles = "not-create")
        int service();
    }

    @Test(expected = NotAuthenticatedException.class)
    public void authenticateWithClassAndeMthodRolesThatNotMatch() {
        ServiceWithClassAndMethodThatNotMatch service = security.secure(ServiceWithClassAndMethodThatNotMatch.class,
                new ServiceWithClassAndMethodThatNotMatch() {

                    @Override
                    public int service() {
                        return 1;
                    }
                });

        service.service();
    }
}
