package cn.itcast.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private Environment env;
	
	@RequestMapping("/info")
	public String info(){
		//System.out.println("123");
		return "HelloWorld123!!"+env.getProperty("url");
	}

}
