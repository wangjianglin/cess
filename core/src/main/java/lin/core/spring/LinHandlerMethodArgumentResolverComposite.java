package lin.core.spring;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 
 * @author lin
 * @date 2015年2月1日 上午1:52:46
 *
 */
//public class LinHandlerMethodArgumentResolverComposite implements HandlerMethodArgumentResolver {
//
//	@Override
//	public boolean supportsParameter(MethodParameter parameter) {
//		Prefix ann = parameter.getParameterAnnotation(Prefix.class);
////		return (ann != null && (Map.class.isAssignableFrom(parameter.getParameterType()))
////				&& !StringUtils.hasText(ann.value()));
//		return ann != null;
//	}
//	
////	private HandlerMethodArgumentResolverComposite resolver;
//
//	@Override
//	public Object resolveArgument(MethodParameter parameter,
//			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
//			WebDataBinderFactory binderFactory) throws Exception {
//		String name = ModelFactory.getNameForParameter(parameter);
//		System.out.println("name:"+name);
////		Object attribute = (mavContainer.containsAttribute(name) ?
////				mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, webRequest));
//
////		WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
////		if (binder.getTarget() != null) {
////			bindRequestParameters(binder, webRequest);
////			validateIfApplicable(binder, parameter);
////			if (binder.getBindingResult().hasErrors()) {
////				if (isBindExceptionRequired(binder, parameter)) {
////					throw new BindException(binder.getBindingResult());
////				}
////			}
////		}
////
////		// Add resolved attribute and BindingResult at the end of the model
////
////		Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
////		mavContainer.removeAttributes(bindingResultModel);
////		mavContainer.addAllAttributes(bindingResultModel);
//
////		return binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
//		return new java.util.Date();
//	}
//
//	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
//		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
//		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
//		servletBinder.bind(servletRequest);
//	}
//}
