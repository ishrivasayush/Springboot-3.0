package com.org.Springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/home/admin")
                    .hasRole("ADMIN")
                    .requestMatchers("/home/normal")
                    .hasRole("NORMAL")
                    .requestMatchers("/home/public")
                    .permitAll()
                    .and()
                    .formLogin();

            return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails normalUser= User.withUsername("ayush")
                .password(passwordEncoder().encode("ayush@123"))
                .roles("NORMAL")
                .build();
        UserDetails adminUser= User.withUsername("abhay")
                .password(passwordEncoder().encode("abhay@123"))
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(normalUser, adminUser);
        return manager;
    }

    @Bean
    public UserDetailsService userDetailsServices()
    {

        return new CustomUserDetailService();
    }
}
