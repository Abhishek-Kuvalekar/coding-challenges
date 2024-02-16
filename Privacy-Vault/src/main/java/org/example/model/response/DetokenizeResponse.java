package org.example.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.miscellaneous.DetokenizeResponseBody;

@Getter
@Setter
@Builder
public class DetokenizeResponse {
    private PrivacyVaultResponse result;
    private DetokenizeResponseBody body;
}
