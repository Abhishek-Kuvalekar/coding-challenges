package org.example.model.miscellaneous;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TokenizeResponseBody {
    private String requestId;
    private Map<String, String> data;
}
