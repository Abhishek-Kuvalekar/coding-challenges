package org.example.factory;

import io.micronaut.context.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.example.config.manager.EncryptionConfigManager;
import org.example.config.nodes.EncryptionConfig;
import org.example.interfaces.IEncryptionService;
import org.example.service.encryption.NoopEncryptionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
@Slf4j
public class EncryptionFactory {
    @Inject
    EncryptionConfigManager encryptionConfigManager;

    @Inject
    BeanContext context;

    public IEncryptionService getEncryptionService() {
        if (!encryptionConfigManager.isEnabled()) {
            log.warn("Encryption is disabled. Values will be stored in plain text");
            return context.getBean(NoopEncryptionService.class);
        }

        Optional<EncryptionConfig.EncryptionTypes.EncryptionType> enabledEncryptionTypeOptional = encryptionConfigManager.getEnabledEncryptionType();
        if (enabledEncryptionTypeOptional.isEmpty()) {
            log.warn("No encryption type is enabled. Values will be stored in plaintext.");
            return context.getBean(NoopEncryptionService.class);
        }

        

    }
}
