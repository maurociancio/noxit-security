package ar.noxit.security;

public interface Security {

    <T> T secure(final Class<T> interfaze, T proxied);
}
