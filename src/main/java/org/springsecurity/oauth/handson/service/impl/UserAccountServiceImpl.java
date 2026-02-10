package org.springsecurity.oauth.handson.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springsecurity.oauth.handson.dto.OAuthUserProfile;
import org.springsecurity.oauth.handson.entity.UserAccount;
import org.springsecurity.oauth.handson.enums.Role;
import org.springsecurity.oauth.handson.repository.UserAccountRepository;
import org.springsecurity.oauth.handson.service.UserAccountService;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserAccount upsertUser(OAuthUserProfile profile) {
        return userAccountRepository.findByProviderAndProviderUserId(profile.getProvider(), profile.getProviderUserId())
                .map(existing -> {
                    existing.setEmail(profile.getEmail());
                    existing.setFullName(profile.getFullName());
                    existing.setPictureUrl(profile.getPictureUrl());
                    existing.setLastLoginAt(Instant.now());
                    return userAccountRepository.save(existing);
                })
                .orElseGet(() -> {
                    UserAccount user = UserAccount.builder()
                            .provider(profile.getProvider())
                            .providerUserId(profile.getProviderUserId())
                            .email(profile.getEmail())
                            .fullName(profile.getFullName())
                            .pictureUrl(profile.getPictureUrl())
                            .roles(Set.of(Role.USER))
                            .createdAt(Instant.now())
                            .lastLoginAt(Instant.now())
                            .build();
                    return userAccountRepository.save(user);
                });
    }
}
