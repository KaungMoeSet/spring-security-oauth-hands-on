package org.springsecurity.oauth.handson.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.authentication.JwtIssuerAuthenticationManagerResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springsecurity.oauth.handson.properties.ResourceServerProperties;
import org.springsecurity.oauth.handson.service.impl.OidcLoginUserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final OidcLoginUserService oidcLoginUserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, ResourceServerProperties props) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/actuator/health/liveness", "/actuator/health/readiness")
						.permitAll()
						.anyRequest()
						.authenticated())
				.oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcLoginUserService))
						.defaultSuccessUrl("/web/current-client", true))
				.oauth2ResourceServer(oauth2 -> oauth2
						.authenticationManagerResolver(
								JwtIssuerAuthenticationManagerResolver.fromTrustedIssuers(
										props.getTrustedIssuers())));

        return http.build();
    }
}
