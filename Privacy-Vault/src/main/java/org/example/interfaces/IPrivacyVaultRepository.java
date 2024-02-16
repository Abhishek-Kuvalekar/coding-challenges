package org.example.interfaces;

import org.example.model.dto.PrivacyVaultDto;

import java.util.List;

public interface IPrivacyVaultRepository {
    boolean saveTokens(List<PrivacyVaultDto> dtos);
    void fetchValues(List<PrivacyVaultDto> dtos);
}
