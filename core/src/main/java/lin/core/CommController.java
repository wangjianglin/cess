package lin.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/core/comm")
//@RequestMapping()
public class CommController{// implements Controller {

	@RequestMapping("/test")
	@ResponseBody
	public String test(@RequestParam("data") String data){
		return data;
	}
	
	
	@ResponseBody
	@RequestMapping("json")
	public String[] json(HttpServletRequest request,HttpServletResponse response){
		//javax.websocket.Session session = null;
		System.out.println("ok.");
		//return new ModelAndView("hello3","message","hello!");
		return new String[]{"one","two"};
	}
	
	@RequestMapping("json2")
	public String[] json2(HttpServletRequest request,HttpServletResponse response){
		//javax.websocket.Session session = null;
		System.out.println("ok.");
		//return new ModelAndView("hello3","message","hello!");
		return new String[]{"one","two"};
	}
}
