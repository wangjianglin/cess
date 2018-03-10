package io.cess.comm.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lin
 * @date Mar 8, 2015 10:24:25 PM
 *
 * http 请求 url，支持url参数，支持 :uid 和 {uid} 两种形式，如：/userinfo/:uid 和 /userinfo/{uid}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface HttpPackageUrl {

	String value();
}
