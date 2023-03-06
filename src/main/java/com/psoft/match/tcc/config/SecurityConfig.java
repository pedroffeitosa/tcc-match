package com.psoft.match.tcc.config;

import com.psoft.match.tcc.security.AuthEntryPoint;
import com.psoft.match.tcc.security.JWTAuthorizationFilter;
import com.psoft.match.tcc.security.JWTUtil;
import com.psoft.match.tcc.service.auth.UserDetailsServiceImpl;
import com.psoft.match.tcc.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JWTUtil jwtUtil;

    private static final String[] PUBLIC_ENDPOINTS = new String[] {
            "/h2/**",
            Constants.BASE_API_ENDPOINT + "/auth/**",
            "/**"
    };

    private static final String [] SWAGGER_ENDPOINTS = new String[] {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v2/api-docs/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .antMatchers(Constants.BASE_API_ENDPOINT + "/admin/**").hasAuthority("ADMIN")
                .antMatchers(Constants.BASE_API_ENDPOINT + "/professor/**").hasAnyAuthority("ADMIN", "PROFESSOR")
                .antMatchers(Constants.BASE_API_ENDPOINT + "/student/**").hasAnyAuthority("ADMIN", "STUDENT")
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthEntryPoint());

        http.addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService, handlerExceptionResolver), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}
