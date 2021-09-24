package com.arfaoui.samples.security.cloudgateway;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

//package com.arfaoui.samples.security.cloudgateway;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	public void configure(HttpSecurity httpSecurity) throws Exception {
//		// @formatter:off
//		httpSecurity.authorizeRequests()
//		            .anyRequest()
//		            .authenticated()
//		            .and()
//		            .sessionManagement()
//		            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		            .and()
//		            .cors()
//		            .and()
//		            .csrf()
//		            .disable()
//		            .oauth2ResourceServer()
//		            .jwt();
//
//		// @formatter:on
//
//	}
//}

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig { //extends WebSecurityConfigurerAdapter {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		// @formatter:off

		http.authorizeExchange(exchanges -> exchanges.anyExchange()
			.authenticated())
		    .oauth2Login(withDefaults());
		http.csrf().disable();
		return http.build();
 
       // @formatter:on
	
//		http
//        .csrf().disable()
//        .httpBasic().disable()
//        .formLogin().disable()
//        .authorizeExchange()
//          .pathMatchers(HttpMethod.OPTIONS).permitAll()
//          .pathMatchers("/user").hasAuthority("USER")
//          .pathMatchers("/admin").hasAuthority("ADMIN")
//        .anyExchange().denyAll()
//        .and()
//        .oauth2ResourceServer()
//          .jwt()
//            .jwtAuthenticationConverter(grantedAuthoritiesExtractor());
	}
}