package org.springsecurity.oauth.handson.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OAuthUserProfile {
    private String provider;
    private String providerUserId;
    private String email;
    private String fullName;
    private String pictureUrl;
}
