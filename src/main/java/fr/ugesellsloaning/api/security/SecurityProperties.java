package fr.ugesellsloaning.api.security;

public class SecurityProperties {
    protected static final String SECRET = "%et?D921IDm4wWyZ2&sQuD8Rg@-*?*w9lcbL4#%sGiMKcwE*0PNZ*j*NOaK0";
    protected static final long EXPIRATION_TIME = 864_000_000; // 10 days
    protected static final String TOKEN_PREFIX = "Bearer ";
    protected static final String HEADER_STRING = "Authorization";
    protected static final String SIGN_UP_URL = "/register";
    protected static final String DEFAULT_INCLUDE_PATTERN = "/api/toRemove.*";
}
