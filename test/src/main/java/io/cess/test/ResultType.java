package io.cess.test;

import java.lang.reflect.Type;

public @interface ResultType {
    Class<?> value();
    Class<?>[] parameterizedType() default {};
}
