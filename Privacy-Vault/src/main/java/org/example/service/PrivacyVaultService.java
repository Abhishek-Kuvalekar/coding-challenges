package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IPrivacyVaultService;
import org.example.model.enums.Action;
import org.example.model.enums.Reason;
import org.example.model.enums.Status;
import org.example.model.request.DetokenizeRequest;
import org.example.model.request.TokenizeRequest;
import org.example.model.response.DetokenizeResponse;
import org.example.model.response.PrivacyVaultResponse;
import org.example.model.response.TokenizeResponse;

import javax.inject.Singleton;
import java.util.Objects;

@Singleton
@Slf4j
public class PrivacyVaultService implements IPrivacyVaultService {
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


        return TokenizeResponse.builder().build();

    }

    @Override
    public DetokenizeResponse detokenize(DetokenizeRequest request) {
        return null;
    }
}
