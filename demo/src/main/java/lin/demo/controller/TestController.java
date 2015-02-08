package lin.demo.controller;

import java.util.List;

import javax.inject.Inject;

import lin.core.spring.Prefix;
import lin.demo.entity.TestEntity;
import lin.demo.services.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

	@Inject
	private TestService service;
	
	
//	@InitBinder("entity")
//	public void initBinder(WebDataBinder binder){
//		binder.setFieldDefaultPrefix("entity.");
//	}
	@RequestMapping("/save/{id}")
	@ResponseBody
//	public TestEntity test(@Prefix("entity")java.util.Date date,TestEntity entity,String name,@PathVariable String id){
//	public TestEntity test(@ModelAttribute("entity")TestEntity entity){
	public TestEntity test(@PathVariable String id,@Prefix()TestEntity entity){
//		TestEntity entity = new TestEntity();
//		service.save(entity);
		return entity;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public String list(){
//		TestEntity entity = new TestEntity();
		 List<TestEntity> list = service.list();
		return "ok."+list.size();
	}
}
