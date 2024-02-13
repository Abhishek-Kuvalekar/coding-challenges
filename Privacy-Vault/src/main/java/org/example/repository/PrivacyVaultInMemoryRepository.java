package org.example.repository;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IPrivacyVaultRepository;
import org.example.model.dto.TokenizeDto;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
@Slf4j
public class PrivacyVaultInMemoryRepository implements IPrivacyVaultRepository {
    private PrivacyVaultStorage storage;

    public PrivacyVaultInMemoryRepository() {
        storage = new PrivacyVaultStorage();
    }

    @Override
    public boolean saveTokens(List<TokenizeDto> dtos) {
        try {
            boolean ans = true;
            for (TokenizeDto dto : dtos) {
                ans = ans && storage.upsert(dto);
            }
            return ans;
        } catch (Exception ex) {
            log.error("Exception while saving tokens: ", ex);
            return false;
        }
    }

    @Override
    public void fetchValues(List<TokenizeDto> dtos) {
        try {
            dtos.stream()
                    .forEach(dto -> {
                        Optional<PrivacyVaultStorage.PrivacyVaultRow> rowOptional = storage.fetch(dto);
                        if (rowOptional.isEmpty()) {
                            dto.setValue(null);
                        } else {
                            dto.setValue(rowOptional.get().getToken());
                        }
                    });

        } catch (Exception ex) {
            log.error("Exception while fetching values: ", ex);
        }
    }

    @Getter
    @Setter
    @Singleton
    private class PrivacyVaultStorage {
        List<PrivacyVaultRow> rows;

        public PrivacyVaultStorage() {
            rows = new ArrayList<>();
        }

        public boolean upsert(TokenizeDto dto) {
            Optional<PrivacyVaultRow> existingRowOptional = rows.stream()
                                                                .filter(row -> row.key.equals(dto.getKey()))
                                                                .findFirst();
            existingRowOptional.ifPresentOrElse(
                    row -> {
                        row.setValue(dto.getValue());
                        row.setToken(dto.getToken());
                        row.setCreatedAt(LocalDateTime.now());
                    },
                    () -> rows.add(
                            PrivacyVaultRow.builder()
                                    .id(rows.size())
                                    .key(dto.getKey())
                                    .value(dto.getValue())
                                    .token(dto.getToken())
                                    .createdAt(LocalDateTime.now())
                                    .updatedAt(LocalDateTime.now())
                                    .build()
                    )
            );
            return true;
        }

        public Optional<PrivacyVaultRow> fetch(TokenizeDto dto)  {
            return rows.stream()
                    .filter(row -> row.getKey().equals(dto.getKey()) && row.getToken().equals(dto.getToken()))
                    .findFirst();
        }

        @Getter
        @Setter
        @Builder
        @ToString
        private class PrivacyVaultRow {
            private long id;
            private String key;
            private String value;
            private String token;
            private LocalDateTime createdAt;
            private LocalDateTime updatedAt;
        }
    }
}
