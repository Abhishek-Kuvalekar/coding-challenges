package cmd.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParsedArguments {
    private Argument<Integer> port;
}
