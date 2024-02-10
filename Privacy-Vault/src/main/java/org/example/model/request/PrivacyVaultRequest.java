package org.example.model.request;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Introspected
public class PrivacyVaultRequest {
    @NotNull
    private String requestId;
    private Map<String, String> data;
}
