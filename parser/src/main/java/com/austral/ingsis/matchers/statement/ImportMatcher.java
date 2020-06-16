package com.austral.ingsis.matchers.statement;

import com.austral.ingsis.*;
import com.austral.ingsis.matchers.StatementMatcher;
import com.austral.ingsis.statements.If;
import com.austral.ingsis.statements.Import;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * (const | let) (identifier) (equals) (literal | number | expression) (semicolon)
 */
public class ImportMatcher extends StatementMatcher<Import> {


    public ImportMatcher(ExpressionParser expressionParser) {
        super(expressionParser);
    }

    @Override
    public Optional<Import> match(Stream<Token> tokens) {
        List<Token> usefulTokens = tokens.filter(super::usefulToken).collect(Collectors.toList());

        Optional<Token> keyWord = Optional.of(usefulTokens.get(0)).filter(token -> token.getType() == TokenType.IMPORT);

        int semiColonIndex = usefulTokens.stream().map(Token::getType).collect(Collectors.toList()).indexOf(TokenType.SEMICOLON);

        if (semiColonIndex == -1) return Optional.empty();

        Optional<? extends Expression> expression = parser.parseExpression(usefulTokens.subList(1, semiColonIndex));

        if (keyWord.isPresent() && expression.isPresent()) {
            return Optional.of(new Import(
                    usefulTokens.get(0).getLine(),
                    usefulTokens.get(0).getIndex(),
                    expression.get()
            ));
        }
        return Optional.empty();
    }
}
