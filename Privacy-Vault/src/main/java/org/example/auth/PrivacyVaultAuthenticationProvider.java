package org.example.auth;

import io.micronaut.core.util.StringUtils;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.example.config.manager.AuthConfigManager;
import org.example.config.nodes.AuthConfig;
import org.example.constant.Constants;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class PrivacyVaultAuthenticationProvider implements AuthenticationProvider {
    @Inject
    AuthConfigManager authConfigManager;

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        String username = (String)authenticationRequest.getIdentity();
        String password = (String)authenticationRequest.getSecret();

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            return Flowable.just(new AuthenticationFailed());

        if (isAdmin(username, password)) {
            return Flowable.just(
                    new UserDetails(
                            Constants.AUTH_USERNAME_ADMIN,
                            List.of(Constants.AUTH_ROLE_ADMIN, Constants.AUTH_ROLE_CONSUMER)
                    )
            );
        }

        if (isConsumer(username, password)) {
            return Flowable.just(
                    new UserDetails(
                            Constants.AUTH_USERNAME_CONSUMER,
                            List.of(Constants.AUTH_ROLE_CONSUMER)
                    )
            );
        }

        return Flowable.just(new AuthenticationFailed());
    }

    private boolean isAdmin(@NotNull String username, @NotNull String password) {
        AuthConfig.AdminAuthCredentials adminAuthCredentials = authConfigManager.getAdminAuthCredentials();

        if (Objects.isNull(adminAuthCredentials)) return false;

        return (
                username.equals(adminAuthCredentials.getUsername()) ||
                password.equals(adminAuthCredentials.getPassword())
        );
    }

    private boolean isConsumer(@NotNull String username, @NotNull String password) {
        AuthConfig.ConsumerAuthCredentials consumerAuthCredentials = authConfigManager.getConsumerAuthCredentials();

        if (Objects.isNull(consumerAuthCredentials)) return false;

        return (
                username.equals(consumerAuthCredentials.getUsername()) ||
                password.equals(consumerAuthCredentials.getPassword())
        );
    }
}
