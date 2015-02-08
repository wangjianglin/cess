package lin.core.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lin.LinException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author lin
 * @date 2015年1月31日 下午7:22:00
 *
 */
//@Component
public class LinExceptionHandler implements HandlerExceptionResolver {

	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception ex) {
		
		if(request.getHeader("__http_comm_protocol__") == null){
			return null;
		}
		long code = -1;
		if(ex instanceof LinException){
			LinException exception = (LinException) ex;
			code = exception.getCode();
		}
		
		ModelAndView mav = new ModelAndView();
//		MappingJacksonJsonView view = new MappingJacksonJsonView();
//        Map<String,Object> attributes = new HashMap<String,Object>();
//        attributes.put("code", code);
//        attributes.put("message", ex.getMessage());
//        if(request.getHeader("__http_comm_protocol_debug__") != null){
//        	ByteArrayOutputStream _out = new ByteArrayOutputStream();
//        	ex.printStackTrace(new PrintStream(_out));
//            attributes.put("strackTrace",_out.toString());
//            
//            if(ex.getCause() != null){
//            	_out = new ByteArrayOutputStream();
//            	ex.getCause().printStackTrace(new PrintStream(_out));
//                attributes.put("cause",_out.toString());
//            }
//        }
//        view.setAttributesMap(attributes);
//        mav.setView(view);
        return mav;
	}

}