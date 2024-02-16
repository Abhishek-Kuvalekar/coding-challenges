package org.example.model.miscellaneous;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class DetokenizeResponseBody {
    private String requestId;
    private Map<String, DetokenizeFieldResponseBody> data;
}
