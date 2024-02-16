package org.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class PrivacyVaultDto {
    private String key;
    private String value;
    private String token;
}
