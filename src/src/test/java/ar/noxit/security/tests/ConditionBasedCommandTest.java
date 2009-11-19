package ar.noxit.security.tests;

import ar.noxit.security.interceptors.Interceptor;
import ar.noxit.security.interceptors.commands.ConditionBasedCommand;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.mocks.ITestService;
import ar.noxit.security.mocks.MockTestService;
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

        ITestService service = this.interceptor.interceptInterface(
                ITestService.class,
                new ConditionBasedCommand<ITestService>(mockTestService) {

                    @Override
                    protected Object onInvocationDenied() throws Throwable {
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

        ITestService service = this.interceptor.interceptInterface(
                ITestService.class,
                new ConditionBasedCommand<ITestService>(mockTestService) {

                    @Override
                    protected Object onInvocationDenied() throws Throwable {
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
