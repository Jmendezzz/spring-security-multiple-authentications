package com.example.multiple_authentications.config;

import com.example.multiple_authentications.config.filters.ApiKeyFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Value("${the.secret}")
  private  String key;
  @Bean
  public SecurityFilterChain securityFilterChainHttp(HttpSecurity http) throws Exception {
    return http.httpBasic()
            .and()
            .addFilterBefore(new ApiKeyFilter(key), BasicAuthenticationFilter.class)
            .authorizeHttpRequests(authz-> authz.anyRequest().authenticated()) // Authorization
            .build();
  }
}
