package com.bezkup.mbare;

import java.util.List;

public interface MbareCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
