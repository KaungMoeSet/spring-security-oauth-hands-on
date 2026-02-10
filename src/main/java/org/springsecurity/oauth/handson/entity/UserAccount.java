package org.springsecurity.oauth.handson.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springsecurity.oauth.handson.enums.Role;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@Document(collection = "users")
public class UserAccount {
    @Id
    private String id;

    private String provider;
    private String providerUserId;

    private String email;
    private String fullName;
    private String pictureUrl;

    private Set<Role> roles;

    private Instant createdAt;
    private Instant lastLoginAt;
}
