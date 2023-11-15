package com.example.multiple_authentications.config.filters;

import com.example.multiple_authentications.config.authentication.ApiKeyAuthentication;
import com.example.multiple_authentications.config.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

  @Value("${the.secret}")
  private  String secret;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    CustomAuthenticationManager manager = new CustomAuthenticationManager(secret);

    String requestKey = request.getHeader("x-api-key");
    if(requestKey == null){ // if the request does not have the api key delegate to the next filter
      filterChain.doFilter(request, response);
      return;
    }
    Authentication authentication = new ApiKeyAuthentication(requestKey);
    try{
      authentication = manager.authenticate(authentication);
      if(authentication.isAuthenticated()){ // if the authentication is successful
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
      }else{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    }catch (AuthenticationException ex){
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }
}
