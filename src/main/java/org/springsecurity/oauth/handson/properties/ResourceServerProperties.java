package org.springsecurity.oauth.handson.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resource-server")
public class ResourceServerProperties {
    private List<String> trustedIssuers = new ArrayList<>();
    public List<String> getTrustedIssuers() { return trustedIssuers; }
    public void setTrustedIssuers(List<String> trustedIssuers) {
        this.trustedIssuers = trustedIssuers;
    }
}
