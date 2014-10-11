package lin.core.spring;

import java.util.Map;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class RequestMapping extends org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping {

	@Override
	public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
		return super.getHandlerMethods();
	}

	
}
