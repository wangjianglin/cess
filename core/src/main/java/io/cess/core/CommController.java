package io.cess.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import io.cess.CessException;
import io.cess.core.spring.CessBody;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;


@Controller
@RequestMapping("/comm")
@Order(Ordered.LOWEST_PRECEDENCE - 100)
@ApiIgnore
public class CommController{

	@RequestMapping("/test")
	@CessBody
	public String test(@RequestParam(value="data",required=false) String data){
		System.out.println("data:"+data);
		return data;
	}
	
	@RequestMapping("/null")
	@CessBody
	public void testNull(){
	}


	@RequestMapping("/sessionId")
	@CessBody
	public String sessionId(HttpServletRequest request,HttpServletResponse response){
		Cookie cookie = new Cookie("name1","value1");
		cookie.setMaxAge(2000000);
		response.addCookie(cookie);


		cookie = new Cookie("name2","value2");
		cookie.setMaxAge(2000000);
		response.addCookie(cookie);

		cookie = new Cookie("name3","value3");
		response.addCookie(cookie);

		return request.getSession().getId();
	}

	@RequestMapping("/ex")
	@CessBody
	public String ex(HttpServletRequest request,String data,Boolean warning){
		System.out.println("method:"+request.getMethod());
		System.out.println("data:"+data);
		if(Boolean.TRUE.equals(warning)){
			CessException.add(new CessException(3,"警告信息1"));
			CessException.add(new CessException(4,"警告信息2"));
			CessException.add(new CessException(5,"警告信息3"));
		}
		if(data == null){
			throw new CessException(-3,"后台错误");
		}
		return data;
	}
}
