package org.example.service;

import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.factory.PrivacyVaultRepoFactory;
import org.example.interfaces.IPrivacyVaultService;
import org.example.model.dto.TokenizeDto;
import org.example.model.enums.Action;
import org.example.model.enums.Reason;
import org.example.model.enums.Status;
import org.example.model.request.DetokenizeRequest;
import org.example.model.request.TokenizeRequest;
import org.example.model.response.DetokenizeResponse;
import org.example.model.response.PrivacyVaultResponse;
import org.example.model.response.TokenizeResponse;
import org.example.repository.PrivacyVaultRepository;
import org.example.service.encryption.EncryptionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.List;

@Singleton
@Slf4j
public class PrivacyVaultService implements IPrivacyVaultService {
    @Inject
    EncryptionService encryptionService;

    @Inject
    PrivacyVaultRepository repository;

    @Override
    public TokenizeResponse tokenize(TokenizeRequest request) {
        if (Objects.isNull(request.getData()) || request.getData().isEmpty()) {
            return TokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.ERROR)
                                    .action(Action.REJECTED)
                                    .reason(Reason.INVALID_DATA)
                                    .build()
                    )
                    .build();
        }

        List<TokenizeDto> tokenizeDtos = new ArrayList<>();
        String token;
        try {
            for (Map.Entry<String, String> entry : request.getData().entrySet()) {
                if (StringUtils.isEmpty(entry.getKey())) continue;
                token = encryptionService.encrypt(entry.getValue());
                tokenizeDtos.add(
                        TokenizeDto.builder()
                                .key(entry.getKey())
                                .value(entry.getValue())
                                .token(token)
                                .build()
                );
            }

            boolean isStored = repository.saveTokens(tokenizeDtos);
            if (!isStored) {
                return TokenizeResponse.builder()
                        .result(
                                PrivacyVaultResponse.builder()
                                        .status(Status.ERROR)
                                        .action(Action.REJECTED)
                                        .reason(Reason.DB_ERROR)
                                        .build()
                        )
                        .build();
            }

            TokenizeResponse.TokenizeResponseBuilder builder = TokenizeResponse.builder();

            return builder.build();
        } catch (Exception ex) {
            log.error("Error while tokenizing.", ex);
            return TokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.ERROR)
                                    .action(Action.REJECTED)
                                    .reason(Reason.PROCESSING_FAILED)
                                    .build()
                    ).build();
        }
    }

    @Override
    public DetokenizeResponse detokenize(DetokenizeRequest request) {
        return null;
    }
}
