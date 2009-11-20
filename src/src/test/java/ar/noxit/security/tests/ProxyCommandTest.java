package ar.noxit.security.tests;

import ar.noxit.security.interceptors.Interceptor;
import ar.noxit.security.interceptors.commands.SimpleProxyCommand;
import ar.noxit.security.interceptors.impl.CGLibInterceptor;
import ar.noxit.security.mocks.services.TestService;
import ar.noxit.security.mocks.services.MockTestService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProxyCommandTest {

    private Interceptor interceptor;

    @Before
    public void setUp() {
        this.interceptor = new CGLibInterceptor();
    }

    @Test
    public void testSimpleProxyIsCalled() {
        MockTestService mockTestService = new MockTestService();

        TestService service = this.interceptor.interceptInterface(
                TestService.class,
                new SimpleProxyCommand<TestService>(mockTestService));

        service.doService();
        assertEquals(mockTestService.getTimesInvoked(), 1);

        service.doService();
        assertEquals(mockTestService.getTimesInvoked(), 2);
    }
}
