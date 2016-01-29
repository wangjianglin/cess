package lin.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lin.core.spring.MessageBody;

@Controller
@RequestMapping("/core/comm")
//@RequestMapping()
public class CommController{// implements Controller {

	@RequestMapping("/test")
	@MessageBody
	public String test(@RequestParam(value="data",required=false) String data){
		System.out.println("data:"+data);
		return data;
	}
	
	@RequestMapping("/null")
	@MessageBody
	public void testNull(){
	}

	
	@MessageBody
	@RequestMapping("/sessionId")
	public String sessionId(HttpServletRequest request,HttpServletResponse response){
		System.out.println("session id:"+request.getSession().getId());
		return request.getSession().getId();
	}
}
