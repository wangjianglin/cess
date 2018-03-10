package io.cess.test;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Repeatable(HttpHeaders.class)
public @interface HttpHeader {

    String name();
    String value();
}
