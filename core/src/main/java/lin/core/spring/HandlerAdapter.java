package lin.core.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

     //org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
public class HandlerAdapter extends RequestMappingHandlerAdapter{

	public HandlerAdapter(){
		
	}
	
	public List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> lists = new ArrayList<HttpMessageConverter<?>>();
		lists.add(new LinMessageConverter());
		return lists;
	}
//@Override
//	public List<HandlerMethodReturnValueHandler> getCustomReturnValueHandlers() {
//		//return super.getCustomReturnValueHandlers();
//	List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
//	handlers.add(new ModelMethodProcessor());
//	return handlers;
//	}


	//	private List<HandlerMethodReturnValueHandler> tmp(){
//	List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
//
//	// Single-purpose return value types
////	handlers.add(new ModelAndViewMethodReturnValueHandler());
////	handlers.add(new ModelMethodProcessor());
////	handlers.add(new ViewMethodReturnValueHandler());
////	handlers.add(new HttpEntityMethodProcessor(getMessageConverters(), this.contentNegotiationManager));
////	handlers.add(new HttpHeadersReturnValueHandler());
////	handlers.add(new CallableMethodReturnValueHandler());
////	handlers.add(new DeferredResultMethodReturnValueHandler());
////	handlers.add(new AsyncTaskMethodReturnValueHandler(this.beanFactory));
//
//	// Annotation-based return value types
////	handlers.add(new ModelAttributeMethodProcessor(false));
//	handlers.add(new RequestResponseBodyMethodProcessor(getMessageConverters(), this.get.contentNegotiationManager));
//
//	// Multi-purpose return value types
////	handlers.add(new ViewNameMethodReturnValueHandler());
////	handlers.add(new MapMethodProcessor());
//
//	// Custom return value types
//	if (getCustomReturnValueHandlers() != null) {
//		handlers.addAll(getCustomReturnValueHandlers());
//	}
//
//	// Catch-all
//	if (!CollectionUtils.isEmpty(getModelAndViewResolvers())) {
//		handlers.add(new ModelAndViewResolverMethodReturnValueHandler(getModelAndViewResolvers()));
//	}
//	else {
//		handlers.add(new ModelAttributeMethodProcessor(true));
//	}
//
//	return handlers;
//}
	@Override
	public List<HandlerMethodReturnValueHandler> getReturnValueHandlers() {
		return super.getReturnValueHandlers();
	}

}
