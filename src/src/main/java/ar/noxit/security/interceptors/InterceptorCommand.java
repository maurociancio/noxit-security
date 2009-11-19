package ar.noxit.security.interceptors;

import java.lang.reflect.Method;

public interface InterceptorCommand<T> {

    Object intercept(T intercepted, Method method, Object[] args) throws Throwable;
}
