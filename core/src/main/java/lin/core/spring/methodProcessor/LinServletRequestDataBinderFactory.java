package lin.core.spring.methodProcessor;

import java.util.List;

import org.springframework.util.Assert;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;


public class LinServletRequestDataBinderFactory extends ServletRequestDataBinderFactory {

	public LinServletRequestDataBinderFactory(
			List<InvocableHandlerMethod> binderMethods,
			WebBindingInitializer initializer) {
		super(binderMethods, initializer);
	}
	
//	@Override
//	protected ServletRequestDataBinder createBinderInstance(Object target, String objectName, NativeWebRequest request) {
//		return new LinExtendedServletRequestDataBinder(target, objectName);
//	}
	
	private ServletRequestDataBinder linCreateBinderInstance(Object target, String objectName, NativeWebRequest request) {
		return new LinExtendedServletRequestDataBinder(target, objectName);
	}

	public final WebDataBinder linCreateBinder(NativeWebRequest webRequest, Object target, String objectName)
			throws Exception {
		WebDataBinder dataBinder = linCreateBinderInstance(target, objectName, webRequest);

		try {
			java.lang.reflect.Field initializerField = DefaultDataBinderFactory.class.getDeclaredField("initializer");
			initializerField.setAccessible(true);
//			bindingResultField.set(this, bindingResult);
			WebBindingInitializer initializer = (WebBindingInitializer) initializerField.get(this);
			if(initializer != null){
				initializer.initBinder(dataBinder, webRequest);
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		initBinder(dataBinder, webRequest);
		return dataBinder;
	}
}
