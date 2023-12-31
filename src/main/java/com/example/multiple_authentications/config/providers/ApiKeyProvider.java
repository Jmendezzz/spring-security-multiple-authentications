package com.example.multiple_authentications.config.providers;

import com.example.multiple_authentications.config.authentication.ApiKeyAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class ApiKeyProvider implements AuthenticationProvider{

  private final String key;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ApiKeyAuthentication auth = (ApiKeyAuthentication) authentication;
    if (key.equals(auth.getApiKey())) {
      auth.setAuthenticated(true);
      return auth;
    }

    throw new BadCredentialsException("Invalid API Key");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiKeyAuthentication.class.equals(authentication);
  }
}
