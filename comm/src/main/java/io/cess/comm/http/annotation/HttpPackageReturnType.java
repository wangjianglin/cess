package io.cess.comm.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lin
 * @date Mar 11, 2015 8:40:47 PM
 *
 * http 请求返回的Java类型
 */
@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpPackageReturnType {

	/**
	 * 返回Java类型
	 * @return
	 */
	Class<?> value();

	/**
	 * 如果返回结果是泛型，指定泛型类型
	 * @return
	 */
	Class<?>[] parameterizedType() default {};
}
