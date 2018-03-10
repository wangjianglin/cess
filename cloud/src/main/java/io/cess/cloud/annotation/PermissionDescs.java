package io.cess.cloud.annotation;


import java.lang.annotation.*;

/**
 * @author lin
 * @date 28/06/2017.
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PermissionDescs {
    PermissionDesc[] value();
}
