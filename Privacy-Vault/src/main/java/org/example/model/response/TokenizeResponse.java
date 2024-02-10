package org.example.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.miscellaneous.TokenizeResponseBody;

@Getter
@Setter
@Builder
public class TokenizeResponse {
    private PrivacyVaultResponse result;
    private TokenizeResponseBody body;
}
