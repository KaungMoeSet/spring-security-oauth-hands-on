package org.springsecurity.oauth.handson.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springsecurity.oauth.handson.service.AuthService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@GetMapping("/web/id-token")
	public Map<String, Object> webIdToken(@AuthenticationPrincipal OidcUser oidcUser) {
		return authService.getWebIdToken(oidcUser);
	}

	@GetMapping("/web/access-token")
	public Map<String, Object> webAccessToken(OAuth2AuthenticationToken authenticationToken) {
		return authService.getWebAccessToken(authenticationToken);
	}

	@GetMapping("/web/current-client") // Directly get access-token
	public Map<String, Object> accessToken(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		return authService.getClient(authorizedClient);
	}

	@GetMapping("/api/id-token")
	public Map<String, Object> apiIdToken(@AuthenticationPrincipal Jwt jwt) {
		return authService.getApiIdToken(jwt);
	}

}
