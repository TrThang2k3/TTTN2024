package com.poly.controllerUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class usercontroller {
	@GetMapping("path")
	public String index() {
		return "/template-usser/ticket";
	}
	
}
