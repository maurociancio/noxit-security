package ar.noxit.security.interceptors;

public interface Interceptor {

    <T> T interceptInterface(Class<T> interfaze, InterceptorCommand<T> command);
}
