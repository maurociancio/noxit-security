package ar.noxit.security.tests.authorizer;

import ar.noxit.security.auth.Authorizer;
import ar.noxit.security.auth.authorization.ArrayBasedAuthorizer;
import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import org.junit.Test;

public class ArrayBasedAuthorizerTest {

    private Authorizer authorizer;

    @Test
    public void testOkArrayBasedAuthorizer() throws AuthException {
        this.authorizer = new ArrayBasedAuthorizer("create");

        this.authorizer.authorize(new String[]{"create"});
        this.authorizer.authorize(new String[]{"create"});
    }

    @Test
    public void testOkUsingArrayForArrayBasedAuthorizer() throws AuthException {
        this.authorizer = new ArrayBasedAuthorizer(new String[]{"read", "modify"});

        this.authorizer.authorize(new String[]{"read"});
        this.authorizer.authorize(new String[]{"modify"});
    }

    @Test(expected = NotAuthenticatedException.class)
    public void testFailUsingArrayForArrayBasedAuthorizer() throws AuthException {
        this.authorizer = new ArrayBasedAuthorizer(new String[]{"read", "modify"});

        this.authorizer.authorize(new String[]{"erase"});
    }

    @Test(expected = NotAuthenticatedException.class)
    public void testFailArrayBasedAuthorizer() throws AuthException {
        this.authorizer = new ArrayBasedAuthorizer("list");

        this.authorizer.authorize(new String[]{"create"});
    }
}
