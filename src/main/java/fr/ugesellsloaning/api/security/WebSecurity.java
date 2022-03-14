package fr.ugesellsloaning.api.security;

import fr.ugesellsloaning.api.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/user/register/").permitAll()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .antMatchers(HttpMethod.GET,"/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authBuilder) throws Exception{
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
}
