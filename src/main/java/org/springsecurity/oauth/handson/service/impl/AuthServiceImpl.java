package org.springsecurity.oauth.handson.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springsecurity.oauth.handson.service.AuthService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public Map<String, Object> getWebIdToken(OidcUser user) {
        if (user == null) {
            return Map.of("error", "No OIDC user available");
        }
        logger.info("User: {}", user);
        return Map.of(
                "id_token_value", user.getIdToken().getTokenValue(),
                "claims", user.getIdToken().getClaims()
        );
    }

	@Override
    public Map<String, Object> getWebAccessToken(OAuth2AuthenticationToken authenticationToken) {
        if (authenticationToken == null) {
            return Map.of("error", "No authentication available");
        }
        logger.info("authentication: {}", authenticationToken);
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        String authName =  authenticationToken.getName();
        logger.info("registrationId: {}", registrationId);
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                registrationId,
                authName
        );
        if (client == null) {
            return Map.of("error", "No authorized client found");
        }

        return Map.of(
                "access_token_value", client.getAccessToken().getTokenValue(),
                "access_token_type", client.getAccessToken().getTokenType().getValue(),
                "expires_at", client.getAccessToken().getExpiresAt(),
				"scopes", client.getAccessToken().getScopes()
		);
	}

	@Override
    public Map<String, Object> getClient(OAuth2AuthorizedClient authorizedClient) {
        if (authorizedClient == null) {
            return Map.of("error", "No authorized client found");
        }
        return Map.of("client", authorizedClient);
    }

	@Override
    public Map<String, Object> getApiIdToken(Jwt jwt) {
        if (jwt == null) {
            return Map.of("error", "No JWT available");
        }
        return Map.of(
                "token_value", jwt.getTokenValue(),
                "claims", jwt.getClaims()
        );
    }

}
