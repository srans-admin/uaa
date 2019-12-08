package com.srans.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.srans.uaa.oauth2service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(3)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
   @Autowired
   private UserService userService;
   
   //@Autowired
   //private PasswordEncoder passwordEncoder;

   //@Bean
   //public PasswordEncoder encoder() {
   //   return new BCryptPasswordEncoder();
   //}
   
   /*@Bean
   public PasswordEncoder customPasswordEncoder() {
       return new PasswordEncoder() {
           @Override
           public String encode(CharSequence rawPassword) {
               return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(4));
           }
           @Override
           public boolean matches(CharSequence rawPassword, String encodedPassword) {
               return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
           }
       };
   }*/
   
   @Bean
   public PasswordEncoder passwordEncoder() {
       return new PasswordEncoder() {
           @Override
           public String encode(CharSequence rawPassword) {
               return rawPassword.toString();
           }
           @Override
           public boolean matches(CharSequence rawPassword, String encodedPassword) {
               return rawPassword.toString().equals(encodedPassword);
           }
       };
   }
   
   @Override
   @Autowired
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
   }
   
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       
	   /*
	   http.requestMatchers()
	       .antMatchers("/login", "/oauth/authorize")
	       .and()
	       .authorizeRequests()
	       .anyRequest().authenticated()
	       .and()
	       .formLogin().permitAll();
	   */
	   http.headers().frameOptions().disable(); // For H2 Console Visibility
	   http.csrf().disable()
	   	   .anonymous().disable()
	   	   .authorizeRequests()
	   	   .antMatchers("/oauth/token").permitAll();
		 
      
   }
   @Override
   public void configure(WebSecurity web) throws Exception {
      web.ignoring();
   }
   
   @Override 
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   } 
} 