package org.swordexplorer.notes.security.model;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.swordexplorer.notes.security.CustomAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // add passwordEncoder
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.authorizeRequests().antMatchers(POST, "/api/login/**").permitAll();
    http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER");
    http.authorizeRequests().antMatchers(POST, "/api/user/save").hasAnyAuthority("ROLE_ADMIN");

    http.authorizeRequests().antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN");
    http.addFilter(customAuthenticationFilter);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
