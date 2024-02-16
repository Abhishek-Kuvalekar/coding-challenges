package org.example.model.miscellaneous;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class PrivacyVaultRow {
    private long id;
    private String key;
    private String value;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}