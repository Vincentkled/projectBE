package com.example.project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;



	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	// @Autowired
	// public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	// 	// configure AuthenticationManager so that it knows from where to load
	// 	// user for matching credentials
	// 	// Use BCryptPasswordEncoder
	// 	auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	// }

    // authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    //authorization
    //editor -> department
    // admin ->region
    //non-login -> login,register forgot password
    //login -> changepassword
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable().authorizeHttpRequests((auth) -> {
            try {
                auth
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/account/**").permitAll()
                .antMatchers("/api/department/**").permitAll()
                .antMatchers("/forgotpassword").permitAll()
                .antMatchers("/changepassword").authenticated()
                .antMatchers("/department/**").permitAll()
                .antMatchers("/assets/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // .logout();

                httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });
        return httpSecurity.build();
    }
    //encrypt
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
    