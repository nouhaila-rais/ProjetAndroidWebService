package fr.ugesellsloaning.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ugesellsloaning.api.entities.User;
import fr.ugesellsloaning.api.services.UserDetailsServiceImpl;
import fr.ugesellsloaning.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;

    public SpringSecurityConfiguration(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String DEFAULT_INCLUDE_PATTERN = "/api/**";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserServices userServices;

    String login = "";

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .cors().and().csrf().disable()
                .authorizeRequests()
                //.antMatchers("/admin/**").hasRole(ADMIN)
                //.antMatchers("/api/**").hasAnyRole(ADMIN, USER)

                .antMatchers(HttpMethod.POST,"/login", "/register").permitAll()
                .antMatchers(HttpMethod.GET,"/logout").authenticated()
                //**** Swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("**/*.html").permitAll()
                .antMatchers("**/*.css").permitAll()
                .antMatchers("**/*.js").permitAll()
                //*** End Swagger conf
                //.anyRequest().authenticated()

                .anyRequest().permitAll()
                .and().formLogin()
                .and().logout().logoutUrl("/logout")
                //.anyRequest().permitAll()
                //.and().formLogin()
                //.and().logout().logoutUrl("/logout")

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(DEFAULT_INCLUDE_PATTERN, new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
    @Bean
    public UsernamePasswordFilter authenticationFilter() throws Exception {
        UsernamePasswordFilter authenticationFilter = new UsernamePasswordFilter();
        authenticationFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
        authenticationFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
        authenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }

    private void loginSuccessHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        login = authentication.getName();
        User u = userServices.getUserByLogin(login);
        objectMapper.writeValue(response.getWriter(), u.getId() );
    }

    private void loginFailureHandler(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException {


        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), -1 );
    }

    public User currentUser(){
        return userServices.getUserByLogin(login);
    }

}
