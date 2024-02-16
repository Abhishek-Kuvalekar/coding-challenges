package org.example.service;

import io.micronaut.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.Constants;
import org.example.interfaces.IPrivacyVaultService;
import org.example.model.dto.PrivacyVaultDto;
import org.example.model.enums.Action;
import org.example.model.enums.Reason;
import org.example.model.enums.Status;
import org.example.model.miscellaneous.DetokenizeFieldResponseBody;
import org.example.model.miscellaneous.DetokenizeResponseBody;
import org.example.model.miscellaneous.TokenizeResponseBody;
import org.example.model.request.DetokenizeRequest;
import org.example.model.request.TokenizeRequest;
import org.example.model.response.DetokenizeResponse;
import org.example.model.response.PrivacyVaultResponse;
import org.example.model.response.TokenizeResponse;
import org.example.repository.PrivacyVaultRepository;
import org.example.service.encryption.EncryptionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;

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

        try {
            List<PrivacyVaultDto> tokenizeDtos = new ArrayList<>();
            for (Map.Entry<String, String> entry : request.getData().entrySet()) {
                if (StringUtils.isEmpty(entry.getKey())) continue;
                tokenizeDtos.add(
                        PrivacyVaultDto.builder()
                                .key(entry.getKey())
                                .value(entry.getValue())
                                .token(encryptionService.encrypt(entry.getValue()))
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

            Map<String, String> map = new HashMap<>();
            tokenizeDtos.forEach(tokenizeDto -> map.put(tokenizeDto.getKey(), tokenizeDto.getToken()));

            return TokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.SUCCESS)
                                    .action(Action.ACCEPTED)
                                    .reason(Reason.SUCCESS)
                                    .build()
                    )
                    .body(
                            TokenizeResponseBody.builder()
                                    .requestId(request.getRequestId())
                                    .data(map)
                                    .build()
                    )
                    .build();
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
        if (Objects.isNull(request.getData()) || request.getData().isEmpty()) {
            return DetokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.ERROR)
                                    .action(Action.REJECTED)
                                    .reason(Reason.INVALID_DATA)
                                    .build()
                    )
                    .build();
        }

        try {
            List<PrivacyVaultDto> detokenizeDtos = new ArrayList<>();
            for (Map.Entry<String, String> entry : request.getData().entrySet()) {
                detokenizeDtos.add(
                        PrivacyVaultDto.builder()
                                .key(entry.getKey())
                                .token(entry.getValue())
                                .build()
                );
            }

            repository.fetchValues(detokenizeDtos);

            Map<String, DetokenizeFieldResponseBody> map = new HashMap<>();
            detokenizeDtos.forEach(detokenizeDto -> {
                map.put(
                        detokenizeDto.getKey(),
                        DetokenizeFieldResponseBody.builder()
                                .found(detokenizeDto.getValue() != null)
                                .value(detokenizeDto.getValue() == null ? Constants.DETOKENIZE_VALUE_NOT_FOUND : detokenizeDto.getValue())
                                .build()
                );
            });

            return DetokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.SUCCESS)
                                    .action(Action.ACCEPTED)
                                    .reason(Reason.SUCCESS)
                                    .build()
                    )
                    .body(
                            DetokenizeResponseBody.builder()
                                    .requestId(request.getRequestId())
                                    .data(map)
                                    .build()
                    )
                    .build();
        } catch (Exception ex) {
            log.error("Error while detokenizing.", ex);
            return DetokenizeResponse.builder()
                    .result(
                            PrivacyVaultResponse.builder()
                                    .status(Status.ERROR)
                                    .action(Action.REJECTED)
                                    .reason(Reason.PROCESSING_FAILED)
                                    .build()
                    ).build();
        }
    }
}
