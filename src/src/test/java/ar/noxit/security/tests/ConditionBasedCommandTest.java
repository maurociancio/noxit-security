package ar.noxit.security.tests;

import ar.noxit.security.exceptions.AuthException;
import ar.noxit.security.exceptions.NotAuthenticatedException;
import ar.noxit.security.interceptors.Interceptor;
import ar.noxit.security.interceptors.commands.ConditionBasedCommand;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.mocks.services.TestService;
import ar.noxit.security.mocks.services.MockTestService;
import java.lang.reflect.Method;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConditionBasedCommandTest {

    private Interceptor interceptor;

    @Before
    public void setUp() {
        this.interceptor = new CGLibInterceptor();
    }

    @Test
    public void testConditionBasedProxyIsCalled() {
        MockTestService mockTestService = new MockTestService();

        TestService service = this.interceptor.interceptInterface(
                TestService.class,
                new ConditionBasedCommand<TestService>(mockTestService) {

                    @Override
                    protected Object onInvocationDenied(Method method, AuthException cause) {
                        throw new IllegalStateException("Should never be called.", cause);
                    }

                    @Override
                    protected void checkInvokeProxy(Method method) {
                    }
                });

        service.doService();
        assertEquals(mockTestService.getTimesInvoked(), 1);

        service.doService();
        assertEquals(mockTestService.getTimesInvoked(), 2);
    }

    @Test(expected = IllegalStateException.class)
    public void testConditionBasedProxyIsNotCalled() {
        MockTestService mockTestService = new MockTestService();

        TestService service = this.interceptor.interceptInterface(
                TestService.class,
                new ConditionBasedCommand<TestService>(mockTestService) {

                    @Override
                    protected Object onInvocationDenied(Method method, AuthException cause) throws AuthException {
                        throw new IllegalStateException("Should be raised.", cause);
                    }

                    @Override
                    protected void checkInvokeProxy(Method method) throws AuthException {
                        throw new NotAuthenticatedException();
                    }
                });

        service.doService();
    }
}
