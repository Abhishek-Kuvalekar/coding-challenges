package org.example.config.nodes;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
@ConfigurationProperties("encryption")
public class EncryptionConfig {
    private boolean enabled;
    private EncryptionTypes types;

    @Setter
    @ToString
    @ConfigurationProperties("types")
    public static class EncryptionTypes {
        private Base64Encryption base64;
        private OtherEncryption other;

        @Getter
        private List<EncryptionType> encryptionTypes = Arrays.<EncryptionType>asList(base64, other);

        @ConfigurationProperties("base64")
        public static class Base64Encryption extends EncryptionType {
        }

        @ConfigurationProperties("other")
        public static class OtherEncryption extends EncryptionType {
        }

        @Getter
        @Setter
        @ToString
        public static abstract class EncryptionType {
            private boolean enabled;
            private String secret;
        }
    }
}
