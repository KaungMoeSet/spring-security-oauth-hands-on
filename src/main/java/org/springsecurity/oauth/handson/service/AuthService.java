package org.springsecurity.oauth.handson.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

public interface AuthService {

    Map<String, Object> getWebIdToken(OidcUser user);

    Map<String, Object> getWebAccessToken(OAuth2AuthenticationToken authenticationToken);

    Map<String, Object> getClient(OAuth2AuthorizedClient authorizedClient);

	Map<String, Object> getApiIdToken(Jwt jwt);

}
