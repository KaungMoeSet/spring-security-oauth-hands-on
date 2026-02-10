package org.springsecurity.oauth.handson.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@Builder
public class GoogleTokenResponse {
    private String idToken;
    private String accessToken;
    private Map<String, Object> idTokenClaims;
    private String accessTokenExpiresAt;

    public static GoogleTokenResponse from(OidcUser oidcUser, OAuth2AuthorizedClient client, String timeZoneHeader) {
        return GoogleTokenResponse.builder()
                .idToken(oidcUser.getIdToken().getTokenValue())
                .accessToken(client.getAccessToken().getTokenValue())
                .idTokenClaims(oidcUser.getClaims())
                .accessTokenExpiresAt(formatInstant(client.getAccessToken().getExpiresAt(), timeZoneHeader))
                .build();
    }

    private static String formatInstant(Instant instant, String timeZoneHeader) {
        if (instant == null) {
            return null;
        }

        ZoneId zoneId = resolveZoneId(timeZoneHeader);
        return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(instant.atZone(zoneId));
    }

    private static ZoneId resolveZoneId(String timeZoneHeader) {
        if (timeZoneHeader == null || timeZoneHeader.isBlank()) {
            return ZoneId.of("UTC");
        }

        try {
            return ZoneId.of(timeZoneHeader.trim());
        } catch (DateTimeException ex) {
            return ZoneId.of("UTC");
        }
    }
}
