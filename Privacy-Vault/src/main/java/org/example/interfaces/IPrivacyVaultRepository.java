package org.example.interfaces;

import org.example.model.dto.TokenizeDto;

import java.util.List;

public interface IPrivacyVaultRepository {
    boolean saveTokens(List<TokenizeDto> dtos);
    void fetchValues(List<TokenizeDto> dtos);
}
