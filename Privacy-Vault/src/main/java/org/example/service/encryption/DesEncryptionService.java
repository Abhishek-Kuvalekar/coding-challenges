package org.example.service.encryption;

import io.micronaut.core.util.StringUtils;
import org.example.config.manager.EncryptionConfigManager;
import org.example.config.nodes.EncryptionConfig;
import org.example.constant.Constants;
import org.example.interfaces.IEncryptionService;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class DesEncryptionService implements IEncryptionService {
    @Inject
    EncryptionConfigManager configManager;

    @Override
    public String encrypt(String input) {
        if (StringUtils.isEmpty(input)) return input;

        try {
            SecretKeySpec keySpec = getSecretKey();
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            byte[] inputBytes = input.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String decrypt(String input) {
        if (StringUtils.isEmpty(input)) return input;

        try {
            SecretKeySpec keySpec = getSecretKey();
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] inputBytes = Base64.getDecoder().decode(input);
            byte[] decryptedBytes = cipher.doFinal(inputBytes);

            return new String(decryptedBytes);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private SecretKeySpec getSecretKey() {
        Optional<EncryptionConfig.EncryptionTypes.EncryptionType> desConfigOptional = configManager.getEncryptionConfig(Constants.CONFIG_ENCRYPTION_TYPE_DES);
        if (desConfigOptional.isEmpty()) throw new RuntimeException("DES config not found");

        EncryptionConfig.EncryptionTypes.EncryptionType desConfig = desConfigOptional.get();

        String secret = desConfig.getSecret();
        if (StringUtils.isEmpty(secret)) throw new RuntimeException("DES secret not found");

        try {
            byte[] secretBytes = Base64.getDecoder().decode(secret);
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            secretBytes = messageDigest.digest(secretBytes);
            secretBytes = Arrays.copyOf(secretBytes, 8);

            return new SecretKeySpec(secretBytes, "DES");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
