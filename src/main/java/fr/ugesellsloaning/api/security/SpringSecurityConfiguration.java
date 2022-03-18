package fr.ugesellsloaning.api.security;

//import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
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
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";
    private static final String DEFAULT_INCLUDE_PATTERN = "/api/**";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
                .antMatchers("/admin/**").hasRole(ADMIN)
                .antMatchers("/api/**").hasAnyRole(ADMIN, USER)
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
                .anyRequest().authenticated()
                .and().formLogin()
                .and().logout().logoutUrl("/logout")
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(DEFAULT_INCLUDE_PATTERN, new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }

}
