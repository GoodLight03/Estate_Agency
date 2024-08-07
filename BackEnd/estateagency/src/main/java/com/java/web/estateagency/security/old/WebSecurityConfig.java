package com.java.web.estateagency.security.old;
// package com.java.web.estateagency.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

// import com.java.web.estateagency.security.jwt.AuthEntryPointJwt;
// import com.java.web.estateagency.security.jwt.AuthTokenFilter;
// import com.java.web.estateagency.security.service.UserDetailsServiceImpl;




// @Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class WebSecurityConfig {
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;
  
//     @Autowired
//     private AuthEntryPointJwt unauthorizedHandler;
  
//     @Bean
//     public AuthTokenFilter authenticationJwtTokenFilter() {
//       return new AuthTokenFilter();
//     }

// //     private static final String[] AUTH_WHITELIST = {
// //       "/swagger-resources",
// //       "/swagger-resources/**",
// //       "/configuration/ui",
// //       "/configuration/security",
// //       "/swagger-ui.html",
// //       "/webjars/**",
// //       "/v3/api-docs/**",
// //       "/api/public/**",
// //       "/api/public/authenticate",
// //       "/actuator/*",
// //       "/swagger-ui/**"
// // };
  
//     @Bean
//     public DaoAuthenticationProvider authenticationProvider() {
//         DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
         
//         authProvider.setUserDetailsService(userDetailsService);
//         authProvider.setPasswordEncoder(passwordEncoder());
     
//         return authProvider;
//     }
    
//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//       return authConfig.getAuthenticationManager();
//     }
  
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//       return new BCryptPasswordEncoder();
//     }    
//     @Bean
//     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
//         requestHandler.setCsrfRequestAttributeName("_csrf");

//       http.cors().and().csrf().disable()
//           .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//           .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//           .authorizeRequests().requestMatchers("/api/auth/**").permitAll()
//           .requestMatchers("/**").permitAll()
//           .anyRequest().authenticated();
      
//       http.authenticationProvider(authenticationProvider());
  
//       http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      
//       return http.build();
//     }


// }
