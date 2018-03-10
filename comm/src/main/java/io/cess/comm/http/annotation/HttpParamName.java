package io.cess.comm.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lin
 * @date Mar 11, 2015 11:23:10 PM
 *
 * http 请求参数
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD})
public @interface HttpParamName {
	/**
	 * 参数名称，默认为字段名称
	 * @return
	 */
	String value() default "";
}
