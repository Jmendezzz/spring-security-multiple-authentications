package com.example.multiple_authentications.config.managers;

import com.example.multiple_authentications.config.providers.ApiKeyProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager{
  @Value("${the.secret}")
  private final String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    AuthenticationProvider provider = new ApiKeyProvider(key);
    if(provider.supports(authentication.getClass())){
      return provider.authenticate(authentication);
    }
    return authentication;
  }
}
