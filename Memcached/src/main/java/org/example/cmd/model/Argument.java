package org.example.cmd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Argument<T> {
    private boolean isFound;
    private T value;
}
