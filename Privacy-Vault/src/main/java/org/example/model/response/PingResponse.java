package org.example.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PingResponse {
    private PrivacyVaultResponse result;
}
