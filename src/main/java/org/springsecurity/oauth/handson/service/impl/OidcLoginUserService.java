package org.springsecurity.oauth.handson.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springsecurity.oauth.handson.dto.OAuthUserProfile;
import org.springsecurity.oauth.handson.service.UserAccountService;

@Service
@RequiredArgsConstructor
public class OidcLoginUserService extends OidcUserService {

    private final UserAccountService userAccountService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        OAuthUserProfile profile = OAuthUserProfile.builder()
                .provider(userRequest.getClientRegistration().getRegistrationId())
                .providerUserId(oidcUser.getSubject())
                .email(oidcUser.getEmail())
                .fullName(oidcUser.getFullName())
                .pictureUrl(oidcUser.getPicture())
                .build();

        userAccountService.upsertUser(profile);

        return oidcUser;
    }
}
