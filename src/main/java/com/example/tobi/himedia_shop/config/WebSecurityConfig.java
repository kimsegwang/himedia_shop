package com.example.tobi.himedia_shop.config;

import com.example.tobi.himedia_shop.config.security.CustomAuthenticationFailureHandler;
import com.example.tobi.himedia_shop.config.security.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/static/**", "/css/**", "/js/**","/img/**"
                );
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationSuccessHandler successHandler,
            CustomAuthenticationFailureHandler failureHandler

    )throws Exception {
        http

                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                    "/admin/**"
                                ).authenticated()  // 이 페이지들은 로그인이 필요
                                .requestMatchers("/admin/**").hasRole("ADMIN")  // ADMIN 페이지는 ADMIN 역할이 필요
                                .anyRequest().permitAll()  // 나머지 요청은 로그인 없이 접근 가능
//                                .requestMatchers("/member/login").permitAll() // 로그인 페이지 허용
//                                .requestMatchers("/admin/**").authenticated() // 관리자 페이지는 로그인 필요
//                                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 역할 필요
//                                .anyRequest().permitAll()
                )
                .formLogin(
                        form -> form
                                .loginPage("/member/login")
                                .loginProcessingUrl("/login")
                                .successHandler(successHandler)
                                .failureHandler(failureHandler)

                )
                .logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/member/login")
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
