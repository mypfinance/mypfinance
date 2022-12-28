package com.project.mypfinance.security;

import com.project.mypfinance.config.ConfigProperties;
import com.project.mypfinance.exception.CustomAccessDeniedHandler;
import com.project.mypfinance.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.project.mypfinance.entities.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfigProperties configProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        auth.inMemoryAuthentication()
                .passwordEncoder(bCryptPasswordEncoder)
                .withUser("koko")
                .password("koko")
                .roles(USER)
                .authorities(ROLE_USER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthFilter = new CustomAuthenticationFilter(
                customAuthenticationEntryPoint(), authenticationManagerBean(), configProperties);
        customAuthFilter.setFilterProcessesUrl("/api/login");

        http.cors(Customizer.withDefaults());
        http.csrf().disable();

//        For Security operations:
        http.authorizeRequests().antMatchers("/", "index", "/css/*", "/js/*", "/api/register/**", "/api/login*", "/api/refresh/token/**").permitAll();

//        For User operations:
        http.authorizeRequests().antMatchers(GET, "/api/users*").hasAnyRole(ADMIN);
        http.authorizeRequests().antMatchers(GET, "/api/user/id*").hasAnyRole(ADMIN);
        http.authorizeRequests().antMatchers(GET, "/api/user").hasAnyRole(ADMIN, USER);
        http.authorizeRequests().antMatchers(PUT, "/api/user/modify").hasAnyRole(ADMIN, USER);
        http.authorizeRequests().antMatchers(POST, "/api/user/save/role", "/api/user/add/role/**").hasAnyRole(ADMIN);
        http.authorizeRequests() .antMatchers(DELETE, "/api/user/delete").hasAnyRole(USER);

//        For Expenses operations:
        http.authorizeRequests().antMatchers(GET, "/api/expense/transactions*").hasAnyRole(ADMIN, USER);
        http.authorizeRequests().antMatchers(GET, "/api/expense*").hasAnyRole(USER);
        http.authorizeRequests().antMatchers(GET, "/api/expense/transactions/category",
                "/api/expense/categories/user", "/api/expense/transactions/date").hasAnyRole(USER);
        http.authorizeRequests().antMatchers(POST,"/api/add/expense/category/**", "/api/add/expense/transaction/**" ).hasAnyRole(USER);
        http.authorizeRequests().antMatchers(PUT,"/api/modify/expense/transaction/**").hasAnyRole(USER);
        http.authorizeRequests().antMatchers(DELETE,"/api/delete/expense/category/user/**",
                "/api/delete/expense/transactions/**", "/api/delete/expense/transaction/**").hasAnyRole(USER);

//        For Income operations:
        http.authorizeRequests().antMatchers(GET, "/api/income/transaction/**").hasAnyRole(ADMIN, USER);
        http.authorizeRequests().antMatchers(GET, "/api/income/transactions/admin").hasAnyRole(ADMIN);
        http.authorizeRequests().antMatchers(GET, "/api/income/transactions/user", "/api/income/transactions/category",
                "/api/income/categories/user", "/api/income/transactions/date").hasAnyRole(USER);
        http.authorizeRequests().antMatchers(POST,"/api/add/income/category/**", "/api/add/income/transaction/**" ).hasAnyRole(USER);
        http.authorizeRequests().antMatchers(PUT,"/api/modify/income/transaction/**").hasAnyRole(USER);
        http.authorizeRequests().antMatchers(DELETE,"/api/delete/income/category/user/**",
                "/api/delete/income/transactions/**", "/api/delete/income/transaction/**").hasAnyRole(USER);

//        Filtering:
        http.authorizeRequests().anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.addFilter(customAuthFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(customAccessDeniedHandler(), configProperties),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8081");
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT));
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.OPTIONS.name(),
                HttpMethod.DELETE.name()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }
}
