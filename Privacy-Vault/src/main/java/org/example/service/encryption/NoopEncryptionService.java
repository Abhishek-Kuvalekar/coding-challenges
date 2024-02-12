package org.example.service.encryption;

import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IEncryptionService;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class NoopEncryptionService implements IEncryptionService {
    @Override
    public String encrypt(String input) {
        return input;
    }

    @Override
    public String decrypt(String input) {
        return input;
    }
}
