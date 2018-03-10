package io.cess.comm.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author lin
 * @date 5/2/16
 *
 * http请求头参数
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface HttpHeaderName {
    /**
     * http 请求头
     * @return
     */
    String value() default "";
}

