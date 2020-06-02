package com.austral.ingsis;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token <T>{
    private final TokenType type;
    private final String lexeme;
    final T literal;
    final int line;


}
