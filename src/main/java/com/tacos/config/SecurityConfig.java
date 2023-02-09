package com.tacos.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  @Autowired
  private UserDetailsService userDetailsService;

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
      web
        .ignoring()
        // Spring Security should completely ignore URLs starting with /resources/
        .requestMatchers("/resources/**");
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    http
      // .csrf()//简化，但会不安全
      // .disable()
      .authorizeHttpRequests() //验证请求处理
      .requestMatchers("/design", "/orders")
      .hasRole("USER") //String ROLE_PREFIX = "ROLE_"
      .requestMatchers("/", "/**")
      .permitAll()
      .requestMatchers("/login/**")
      .anonymous()
      .anyRequest()
      .authenticated()
      .and()
      .httpBasic()
      .and()
      .formLogin() //当springcecurity 断定用户没有认证并且需要登录时调用
      .loginPage("/login")
      .loginProcessingUrl("/authenticate") //与登录表单同名
      .defaultSuccessUrl("/design", true)
      .failureUrl("/login?error")
      .usernameParameter("username")
      .passwordParameter("password")
      .and()
      .logout()
      .logoutSuccessUrl("/");

    //anyRequest().authenticated() will restrict the access for any other endpoint other than PUBLIC_URL, and the user must be authenticated.
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    HttpSecurity http,
    PasswordEncoder passwordEncoder,
    UserDetailsService userDetailService
  )
    throws Exception {
    return http
      .getSharedObject(AuthenticationManagerBuilder.class)
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder)
      .and()
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
