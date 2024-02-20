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
        private DesEncryption des;

        public List<EncryptionType> getEncryptionTypes() {
            return Arrays.asList(base64, des);
        }

        @ConfigurationProperties("base64")
        public static class Base64Encryption extends EncryptionType {
        }

        @ConfigurationProperties("des")
        public static class DesEncryption extends EncryptionType {
        }

        @Getter
        @Setter
        @ToString
        public abstract static class EncryptionType {
            private String name;
            private boolean enabled;
            private String secret;
        }
    }
}
