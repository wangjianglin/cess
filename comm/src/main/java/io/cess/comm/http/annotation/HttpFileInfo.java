package io.cess.comm.http.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author lin
 * @date Jun 26, 2015 11:46:37 AM
 *
 * 当http请求参数值为文件类型时（byte[]、File、InputStream），描述文件信息，
 *
 */
@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpFileInfo {
	/**
	 * http 文件上传文件名参数
	 * @return
	 */
	String fileName() default "";

	/**
	 * http mime-type 类型
	 * @return
	 */
	String mimeType() default "application/octet-stream";
}
