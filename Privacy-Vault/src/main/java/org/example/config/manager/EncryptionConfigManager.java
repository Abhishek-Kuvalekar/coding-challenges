package org.example.config.manager;

import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.config.nodes.EncryptionConfig;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;

@Singleton
@Slf4j
public class EncryptionConfigManager {
    @Inject
    EncryptionConfig config;

    public boolean isEnabled() {
        return (Objects.nonNull(config) && config.isEnabled());
    }

    public Optional<EncryptionConfig.EncryptionTypes.EncryptionType> getEncryptionConfig(String name) {
        if (StringUtils.isEmpty(name)) return Optional.empty();

        if (Objects.isNull(config)) return Optional.empty();

        EncryptionConfig.EncryptionTypes encryptionTypes = config.getTypes();
        if (Objects.isNull(encryptionTypes)) return Optional.empty();

        return encryptionTypes
                .getEncryptionTypes()
                .stream()
                .filter(encryptionType -> name.equalsIgnoreCase(encryptionType.getName()))
                .findFirst();
    }

    public Optional<EncryptionConfig.EncryptionTypes.EncryptionType> getEnabledEncryptionType() {
        if (Objects.isNull(config)) return Optional.empty();

        EncryptionConfig.EncryptionTypes encryptionTypes = config.getTypes();
        if (Objects.isNull(encryptionTypes)) return Optional.empty();

        return encryptionTypes
                .getEncryptionTypes()
                .stream()
                .filter(EncryptionConfig.EncryptionTypes.EncryptionType::isEnabled)
                .findFirst();
    }
}
