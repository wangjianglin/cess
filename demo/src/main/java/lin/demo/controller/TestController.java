package lin.demo.controller;

import java.util.List;

import javax.inject.Inject;

import lin.demo.entity.TestEntity;
import lin.demo.services.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

	@Inject
	private TestService service;
	
	@RequestMapping("/save")
	@ResponseBody
	public String test(){
		TestEntity entity = new TestEntity();
		service.save(entity);
		return "ok.";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(){
//		TestEntity entity = new TestEntity();
		 List<TestEntity> list = service.list();
		return "ok."+list.size();
	}
}
