package org.example.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.factory.PrivacyVaultRepoFactory;
import org.example.interfaces.IPrivacyVaultRepository;
import org.example.model.dto.TokenizeDto;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Slf4j
public class PrivacyVaultRepository implements IPrivacyVaultRepository {
    @Inject
    PrivacyVaultRepoFactory factory;

    @Override
    public boolean saveTokens(List<TokenizeDto> dtos) {
        IPrivacyVaultRepository repository = factory.getPrivacyVaultRepo();
        return repository.saveTokens(dtos);
    }

    @Override
    public void fetchValues(List<TokenizeDto> dtos) {
        IPrivacyVaultRepository repository = factory.getPrivacyVaultRepo();
        repository.fetchValues(dtos);
    }
}
