package ar.noxit.security.interceptors.impl;

import ar.noxit.security.interceptors.Interceptor;
import ar.noxit.security.interceptors.InterceptorCommand;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CGLibInterceptor implements Interceptor {

    @Override
    @SuppressWarnings("unchecked")
    public <T> T interceptInterface(Class<T> interfaze, final InterceptorCommand<T> command) {
        Class<?> interfaces[] = new Class<?>[]{interfaze};

        return (T) Enhancer.create(interfaze, interfaces, new MethodInterceptor() {

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                return command.intercept((T) obj, method, args);
            }
        });
    }
}
