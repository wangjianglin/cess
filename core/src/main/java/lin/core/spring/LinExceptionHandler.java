package lin.core.spring;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lin.LinException;
import lin.core.Constants;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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
		
		if(request.getHeader(Constants.HTTP_COMM_PROTOCOL) == null){
			return null;
		}
		long code = -1;
		if(ex instanceof LinException){
			LinException exception = (LinException) ex;
			code = exception.getCode();
		}
		
		ModelAndView mav = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String,Object> attributes = new HashMap<String,Object>();
        attributes.put("code", code);
        attributes.put("message", "未知错误");
        if(request.getHeader(Constants.HTTP_COMM_PROTOCOL_DEBUG) != null){
        	ByteArrayOutputStream _out = new ByteArrayOutputStream();
        	ex.printStackTrace(new PrintStream(_out));
            attributes.put("strackTrace",_out.toString());
            
            if(ex.getCause() != null){
            	_out = new ByteArrayOutputStream();
            	ex.getCause().printStackTrace(new PrintStream(_out));
                attributes.put("cause",_out.toString());
            }
        }
        view.setAttributesMap(attributes);
        mav.setView(view);
        return mav;
	}

}