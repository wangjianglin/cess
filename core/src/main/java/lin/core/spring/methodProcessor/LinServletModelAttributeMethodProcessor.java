package lin.core.spring.methodProcessor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletRequest;

import lin.core.spring.Prefix;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.bind.support.WebRequestDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

public class LinServletModelAttributeMethodProcessor implements HandlerMethodArgumentResolver, HandlerMethodReturnValueHandler {

//	public LinServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
//		super(annotationNotRequired);
//	}

	protected final Object createAttribute(String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

		String value = getRequestValueForAttribute(attributeName, request);
		if (value != null) {
			Object attribute = createAttributeFromRequestValue(value, attributeName, parameter, binderFactory, request);
			if (attribute != null) {
				return attribute;
			}
		}

		return this.createAttribute2(attributeName, parameter, binderFactory, request);
	}

	/**
	 * Obtain a value from the request that may be used to instantiate the
	 * model attribute through type conversion from String to the target type.
	 * <p>The default implementation looks for the attribute name to match
	 * a URI variable first and then a request parameter.
	 * @param attributeName the model attribute name
	 * @param request the current request
	 * @return the request value to try to convert or {@code null}
	 */
	protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
		Map<String, String> variables = getUriTemplateVariables(request);
		if (StringUtils.hasText(variables.get(attributeName))) {
			return variables.get(attributeName);
		}
		else if (StringUtils.hasText(request.getParameter(attributeName))) {
			return request.getParameter(attributeName);
		}
		else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
		Map<String, String> variables =
				(Map<String, String>) request.getAttribute(
						HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		return (variables != null ? variables : Collections.<String, String>emptyMap());
	}

	/**
	 * Create a model attribute from a String request value (e.g. URI template
	 * variable, request parameter) using type conversion.
	 * <p>The default implementation converts only if there a registered
	 * {@link Converter} that can perform the conversion.
	 * @param sourceValue the source value to create the model attribute from
	 * @param attributeName the name of the attribute, never {@code null}
	 * @param parameter the method parameter
	 * @param binderFactory for creating WebDataBinder instance
	 * @param request the current request
	 * @return the created model attribute, or {@code null}
	 * @throws Exception
	 */
	protected Object createAttributeFromRequestValue(String sourceValue, String attributeName,
			MethodParameter parameter, WebDataBinderFactory binderFactory, NativeWebRequest request)
			throws Exception {

		DataBinder binder = binderFactory.createBinder(request, null, attributeName);
		ConversionService conversionService = binder.getConversionService();
		if (conversionService != null) {
			TypeDescriptor source = TypeDescriptor.valueOf(String.class);
			TypeDescriptor target = new TypeDescriptor(parameter);
			if (conversionService.canConvert(source, target)) {
				return binder.convertIfNecessary(sourceValue, parameter.getParameterType(), parameter);
			}
		}
		return null;
	}

	/**
	 * This implementation downcasts {@link WebDataBinder} to
	 * {@link ServletRequestDataBinder} before binding.
	 * @see ServletRequestDataBinderFactory
	 */
//	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}

	//-----------------------------------------------------------
	protected Log logger = LogFactory.getLog(this.getClass());

	private final boolean annotationNotRequired;


	/**
	 * @param annotationNotRequired if "true", non-simple method arguments and
	 * return values are considered model attributes with or without a
	 * {@code @ModelAttribute} annotation.
	 */
	public LinServletModelAttributeMethodProcessor(boolean annotationNotRequired) {
		this.annotationNotRequired = annotationNotRequired;
	}


	/**
	 * @return true if the parameter is annotated with {@link ModelAttribute}
	 * or in default resolution mode also if it is not a simple type.
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(Prefix.class)) {
			return true;
		}
		return false;
//		else if (this.annotationNotRequired) {
//			return !BeanUtils.isSimpleProperty(parameter.getParameterType());
//		}
//		else {
//			return false;
//		}
	}

	/**
	 * Resolve the argument from the model or if not found instantiate it with
	 * its default if it is available. The model attribute is then populated
	 * with request values via data binding and optionally validated
	 * if {@code @java.validation.Valid} is present on the argument.
	 * @throws BindException if data binding and validation result in an error
	 * and the next method parameter is not of type {@link Errors}.
	 * @throws Exception if WebDataBinder initialization fails.
	 */
	@Override
	public final Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

//		String name = ModelFactory.getNameForParameter(parameter);
		String name = parameter.getParameterName();
		Object attribute = (mavContainer.containsAttribute(name) ?
				mavContainer.getModel().get(name) : createAttribute(name, parameter, binderFactory, webRequest));

		WebDataBinder binder = null;
		if(binderFactory instanceof LinServletRequestDataBinderFactory){
			binder = ((LinServletRequestDataBinderFactory)binderFactory).linCreateBinder(webRequest, attribute, name);
		}
		if (binder.getTarget() != null) {
			bindRequestParameters(binder, webRequest);
			validateIfApplicable(binder, parameter);
			if (binder.getBindingResult().hasErrors()) {
				if (isBindExceptionRequired(binder, parameter)) {
					throw new BindException(binder.getBindingResult());
				}
			}
		}

		// Add resolved attribute and BindingResult at the end of the model

		Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
		mavContainer.removeAttributes(bindingResultModel);
		mavContainer.addAllAttributes(bindingResultModel);

		return binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
	}

	/**
	 * Extension point to create the model attribute if not found in the model.
	 * The default implementation uses the default constructor.
	 * @param attributeName the name of the attribute (never {@code null})
	 * @param parameter the method parameter
	 * @param binderFactory for creating WebDataBinder instance
	 * @param request the current request
	 * @return the created model attribute (never {@code null})
	 */
	protected Object createAttribute2(String attributeName, MethodParameter parameter,
			WebDataBinderFactory binderFactory,  NativeWebRequest request) throws Exception {

		return BeanUtils.instantiateClass(parameter.getParameterType());
	}

	/**
	 * Extension point to bind the request to the target object.
	 * @param binder the data binder instance to use for the binding
	 * @param request the current request
	 */
	protected void bindRequestParameters2(WebDataBinder binder, NativeWebRequest request) {
		((WebRequestDataBinder) binder).bind(request);
	}

	/**
	 * Validate the model attribute if applicable.
	 * <p>The default implementation checks for {@code @javax.validation.Valid}.
	 * @param binder the DataBinder to be used
	 * @param parameter the method parameter
	 */
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		Annotation[] annotations = parameter.getParameterAnnotations();
		for (Annotation ann : annotations) {
			Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
			if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
				Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
				Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
				binder.validate(validationHints);
				break;
			}
		}
	}

	/**
	 * Whether to raise a {@link BindException} on validation errors.
	 * @param binder the data binder used to perform data binding
	 * @param parameter the method argument
	 * @return {@code true} if the next method argument is not of type {@link Errors}.
	 */
	protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
		int i = parameter.getParameterIndex();
		Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
		boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));

		return !hasBindingResult;
	}

	/**
	 * Return {@code true} if there is a method-level {@code @ModelAttribute}
	 * or if it is a non-simple type when {@code annotationNotRequired=true}.
	 */
	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		if (returnType.getMethodAnnotation(Prefix.class) != null) {
			return true;
		}
		else if (this.annotationNotRequired) {
			return !BeanUtils.isSimpleProperty(returnType.getParameterType());
		}
		else {
			return false;
		}
	}

	/**
	 * Add non-null return values to the {@link ModelAndViewContainer}.
	 */
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {

		if (returnValue != null) {
			String name = ModelFactory.getNameForReturnValue(returnValue, returnType);
			mavContainer.addAttribute(name, returnValue);
		}
	}

}
