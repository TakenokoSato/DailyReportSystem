package com.techacademy;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    /** 認証・認可設定 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.formLogin(login -> login
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()
              ).logout(logout -> logout
                      .logoutSuccessUrl("/login")
                      ).authorizeHttpRequests(auth -> auth
                         .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                            .permitAll()
                         ).authorizeHttpRequests(auth -> auth
                                      .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                      .permitAll()                 // css等は未ログインでアクセス可
                                  .mvcMatchers("/employee/**").hasAuthority("管理者") // 追記部分：従業員管理は管理者のみアクセス可
                                  .anyRequest().authenticated()   // その他はログイン必要
                              );
        return http.build();
    }

    /** ハッシュ化したパスワードの比較に使用する */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
