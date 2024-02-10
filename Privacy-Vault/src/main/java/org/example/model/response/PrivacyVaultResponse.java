package org.example.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.enums.Action;
import org.example.model.enums.Reason;
import org.example.model.enums.Status;

@Getter
@Setter
@Builder
public class PrivacyVaultResponse {
    private Status status;
    private Action action;
    private Reason reason;
}
