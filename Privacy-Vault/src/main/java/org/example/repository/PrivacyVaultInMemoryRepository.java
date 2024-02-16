package org.example.repository;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.example.interfaces.IPrivacyVaultRepository;
import org.example.model.dto.PrivacyVaultDto;
import org.example.model.miscellaneous.PrivacyVaultRow;

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
    public boolean saveTokens(List<PrivacyVaultDto> dtos) {
        try {
            boolean ans = true;
            for (PrivacyVaultDto dto : dtos) {
                ans = ans && storage.upsert(dto);
            }
            return ans;
        } catch (Exception ex) {
            log.error("Exception while saving tokens: ", ex);
            return false;
        }
    }

    @Override
    public void fetchValues(List<PrivacyVaultDto> dtos) {
        try {
            dtos.stream()
                    .forEach(dto -> {
                        Optional<PrivacyVaultRow> rowOptional = storage.fetch(dto);
                        if (rowOptional.isEmpty()) {
                            dto.setValue(null);
                        } else {
                            dto.setValue(rowOptional.get().getValue());
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

        public boolean upsert(PrivacyVaultDto dto) {
            Optional<PrivacyVaultRow> existingRowOptional = rows.stream()
                                                                .filter(row -> row.getKey().equals(dto.getKey()))
                                                                .findFirst();
            existingRowOptional.ifPresentOrElse(
                    row -> {
                        row.setValue(dto.getValue());
                        row.setToken(dto.getToken());
                        row.setUpdatedAt(LocalDateTime.now());
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

        public Optional<PrivacyVaultRow> fetch(PrivacyVaultDto dto)  {
            return rows.stream()
                    .filter(row -> row.getKey().equals(dto.getKey()) && row.getToken().equals(dto.getToken()))
                    .findFirst();
        }
    }
}
