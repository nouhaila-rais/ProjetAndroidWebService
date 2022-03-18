package fr.ugesellsloaning.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ugesellsloaning.api.entities.User;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    public UsernamePasswordFilter() {
        super();
    }

    private static final String ERROR_MESSAGE = "Something went wrong while parsing /login request body";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User userEntity = new ObjectMapper().readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userEntity.getLogin(), userEntity.getPassword());

            setDetails(request, token);

            return this.getAuthenticationManager().authenticate(token);
        } catch(IOException e) {
            throw new InternalAuthenticationServiceException(ERROR_MESSAGE, e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,FilterChain filter, Authentication authResult)
            throws IOException, ServletException {
            super.successfulAuthentication(request, response,filter, authResult);
    }
}