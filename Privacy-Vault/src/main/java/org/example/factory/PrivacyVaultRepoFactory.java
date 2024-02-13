package org.example.factory;

import io.micronaut.context.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IPrivacyVaultRepository;
import org.example.repository.PrivacyVaultInMemoryRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Slf4j
public class PrivacyVaultRepoFactory {
    @Inject
    BeanContext context;

    public IPrivacyVaultRepository getPrivacyVaultRepo() {
        return context.getBean(PrivacyVaultInMemoryRepository.class);
    }
}
