package org.springsecurity.oauth.handson.service;

import org.springsecurity.oauth.handson.dto.OAuthUserProfile;
import org.springsecurity.oauth.handson.entity.UserAccount;

public interface UserAccountService {
    UserAccount upsertUser(OAuthUserProfile profile);
}
