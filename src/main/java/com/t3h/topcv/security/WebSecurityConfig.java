package com.t3h.topcv.security;

import com.t3h.topcv.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    public WebSecurityConfig(@Lazy UserService accountService, JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = accountService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //authenticationProvider bean definition
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserService accountService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(accountService); //set the custom account details service
//        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
//        return auth;
//    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
//                                .requestMatchers("/").hasRole("EMPLOYEE")
//                                .requestMatchers("/leaders/**").hasRole("MANAGER")
//                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                .requestMatchers("candidates/**").permitAll()
                                .requestMatchers("auth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll
                );
//                .exceptionHandling(configurer ->
//                        configurer.accessDeniedPage("/access-denied")
//                );

        // use HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        http.cors(AbstractHttpConfigurer::disable);

        // disable Cross-Site Request Forgery (CSRF)
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}