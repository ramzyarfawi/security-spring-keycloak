package com.arfaoui.samples.security.microservice1;

import static org.springframework.http.HttpMethod.GET;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ms1")
public class Microservice1Controller {

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();
//	private final WebClient webClient = WebClient.builder().build();

	@GetMapping("/jwt")
	public String whoami(@AuthenticationPrincipal Jwt jwt) {
		final String userName = String.format("Hello, %s", jwt.getClaim("preferred_username").toString());
		return userName;
	}

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello from microservice1";
	}

	@PreAuthorize("hasAuthority('SCOPE_TEST')")
	@GetMapping("/ping")
	public String ping() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		return "Scopes: " + authentication.getAuthorities();
	}

	@GetMapping("/roles")
	public String roles() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		Set<String> roles = authentication.getAuthorities().stream()
			     .map(r -> r.getAuthority()).collect(Collectors.toSet());
		final String rolz = "";
		roles.stream().forEach(r-> {
			rolz.concat("," + r);
			System.out.println(r);
		});

		return "Roles: " + roles;
	}

	@PreAuthorize("hasAnyRole('ROLE_SYR_ADMIN','ROLE_SYR_BUSINESS')")
	@GetMapping("/home/rest-template")
	@ResponseStatus(HttpStatus.OK)
	public String helloRestTemplate() {
		Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", "Bearer " + /* null */jwt.getTokenValue());
		ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8083/ms2/hello", GET,
				new HttpEntity<>(httpHeaders), String.class);

		return "hello - message from microservice 2 -  " + exchange.getBody();
	}

	@PreAuthorize("hasRole('ROLE_SYR_ADMIN')")
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

//    @GetMapping("/microservice1/home/webclient")
//    @ResponseStatus(HttpStatus.OK)
//    public String helloWebClient() {
//        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String response = webClient.get()
//                .uri("http://localhost:8084/microservice2/home")
//                .headers(header -> header.setBearerAuth(jwt.getTokenValue()))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//        return "hello - message from microservice 2 -  " + response;
//    }
}