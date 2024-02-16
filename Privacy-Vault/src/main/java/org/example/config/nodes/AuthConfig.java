package org.example.config.nodes;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ConfigurationProperties("auth")
public class AuthConfig {
    private AdminAuthCredentials admin;
    private ConsumerAuthCredentials consumer;

    @ConfigurationProperties("admin")
    public static class AdminAuthCredentials extends AuthCredentials {

    }

    @ConfigurationProperties("consumer")
    public static class ConsumerAuthCredentials extends AuthCredentials {

    }

    @Getter
    @Setter
    public abstract static class AuthCredentials {
        private String username;
        private String password;
    }
}
