package com.austral.ingsis;

import com.austral.ingsis.value.Value;

public interface Resolver {

    Value resolve(Expression expression, Boolean isConstant);

    Value resolve(Expression expression);

}
