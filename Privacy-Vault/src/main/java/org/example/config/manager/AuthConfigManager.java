package org.example.config.manager;

import lombok.extern.slf4j.Slf4j;
import org.example.config.nodes.AuthConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Slf4j
public class AuthConfigManager {
    @Inject
    AuthConfig authConfig;

    public AuthConfig.AdminAuthCredentials getAdminAuthCredentials() {
        return authConfig.getAdmin();
    }

    public AuthConfig.ConsumerAuthCredentials getConsumerAuthCredentials() {
        return authConfig.getConsumer();
    }
}
