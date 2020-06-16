package com.austral.ingsis.scope.helpers;

import com.austral.ingsis.Expression;
import com.austral.ingsis.value.Value;

public interface Resolver {

    Value resolve(Expression expression, Boolean isConstant);

    Value resolve(Expression expression);

}
