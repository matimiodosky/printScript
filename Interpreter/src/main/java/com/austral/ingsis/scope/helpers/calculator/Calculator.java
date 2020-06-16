package com.austral.ingsis.scope.helpers.calculator;

import com.austral.ingsis.value.NumberValue;
import com.austral.ingsis.value.StringValue;
import com.austral.ingsis.value.Value;

public interface Calculator {

    Value calculate(NumberValue left, NumberValue right, String operator);

    Value calculate(NumberValue asNumber, StringValue asString, String operator);

    Value calculate(StringValue left, NumberValue right, String operator);

    Value calculate(StringValue left, StringValue right, String operator);

}
