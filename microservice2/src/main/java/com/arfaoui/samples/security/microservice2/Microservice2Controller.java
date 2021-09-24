package com.arfaoui.samples.security.microservice2;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms2")
public class Microservice2Controller {

	@PreAuthorize("hasAnyRole('ROLE_SYR_ADMIN','ROLE_SYR_SYPEL')")
	@GetMapping("/hello")
	@ResponseStatus(HttpStatus.OK)
	public String hello() {
		return "Hello from microservice2";
	}

	@PreAuthorize("hasRole('ROLE_SYR_BUSINESS')")
	@GetMapping("/syradmin")
	public String syrAdmin() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "Scopes: " + authentication.getAuthorities();
	}

	@PreAuthorize("hasRole('ROLE_SYR_BUSINESS')")
	@GetMapping("/syrbusiness")
	public String syrBusiness() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "Scopes: " + authentication.getAuthorities();
	}

	@PreAuthorize("hasRole('ROLE_SYR_SYPEL')")
	@GetMapping("/syrsypel")
	public String syrSypel() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "Scopes: " + authentication.getAuthorities();
	}

	@PreAuthorize("hasRole('ROLE_SYR_ALL')")
	@GetMapping("/syrall")
	public String syrAll() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "Scopes: " + authentication.getAuthorities();
	}
}