package ar.noxit.security;

public interface Security {

    <T> T secure(Class<T> interfaze, T proxied);
}
