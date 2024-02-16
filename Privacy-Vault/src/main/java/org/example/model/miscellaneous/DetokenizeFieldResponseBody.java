package org.example.model.miscellaneous;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DetokenizeFieldResponseBody {
    private boolean found;
    private String value;
}
