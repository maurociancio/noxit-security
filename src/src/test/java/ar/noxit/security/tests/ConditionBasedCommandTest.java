package ar.noxit.security.tests;

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
                    protected Object onInvocationDenied(Method method) throws Throwable {
                        throw new IllegalStateException("Should never be called.");
                    }

                    @Override
                    protected boolean shouldInvokeProxy(Method method) {
                        return true;
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
                    protected Object onInvocationDenied(Method method) throws Throwable {
                        throw new IllegalStateException("Should be raised.");
                    }

                    @Override
                    protected boolean shouldInvokeProxy(Method method) {
                        return false;
                    }
                });

        service.doService();
    }
}
