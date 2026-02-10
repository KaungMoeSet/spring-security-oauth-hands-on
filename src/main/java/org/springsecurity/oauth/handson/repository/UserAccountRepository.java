package org.springsecurity.oauth.handson.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springsecurity.oauth.handson.entity.UserAccount;

import java.util.Optional;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

    Optional<UserAccount> findByProviderAndProviderUserId(String provider, String providerUserId);

    Optional<UserAccount> findByEmail(String email);
}
