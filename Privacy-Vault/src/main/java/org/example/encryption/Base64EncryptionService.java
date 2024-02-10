package org.example.encryption;

import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IEncryptionService;

import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Singleton
@Slf4j
public class Base64EncryptionService implements IEncryptionService {

    @Override
    public String encrypt(String input) {
        if (StringUtils.isEmpty(input)) return input;
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decrypt(String input) {
        if (StringUtils.isEmpty(input)) return input;
        return new String(Base64.getDecoder().decode(input.getBytes(StandardCharsets.UTF_8)));
    }
}
