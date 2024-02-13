package org.example.service.encryption;

import lombok.extern.slf4j.Slf4j;
import org.example.factory.EncryptionFactory;
import org.example.interfaces.IEncryptionService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Slf4j
public class EncryptionService implements IEncryptionService {
    @Inject
    EncryptionFactory factory;

    @Override
    public String encrypt(String input) {
        IEncryptionService encryptionService = factory.getEncryptionService();
        return encryptionService.encrypt(input);
    }

    @Override
    public String decrypt(String input) {
        IEncryptionService encryptionService = factory.getEncryptionService();
        return encryptionService.decrypt(input);
    }
}
