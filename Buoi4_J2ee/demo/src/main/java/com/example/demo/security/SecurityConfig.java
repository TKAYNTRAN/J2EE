package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Existing thymeleaf forms do not include CSRF tokens -> disable to avoid 403 on POST.
        http.csrf().disable();

        http.authorizeRequests()
                // public
                .antMatchers("/", "/login", "/register", "/error", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                // endpoints from the assignment image (keep for the old MVC UI)
                .antMatchers(
                        "/products/add",
                        "/products/edit",
                        "/products/delete"
                ).hasRole("ADMIN")
                .antMatchers("/products", "/products/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/order", "/order/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/products", true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll();

        return http.build();
    }
}
