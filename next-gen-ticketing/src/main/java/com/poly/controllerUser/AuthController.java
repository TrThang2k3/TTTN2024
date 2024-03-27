package com.poly.controllerUser;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
	
	@RequestMapping("/nextgen.com/login")
	public String login() {
		return "/template-user/login";
	}

	@RequestMapping("/nextgen.com/login/success")
	public String success(Model model) {
		model.addAttribute("message", "Login successful!");
		return "redirect:/nextgen.com";
	}

	@RequestMapping("/nextgen.com/login/error")
	public String error(Model model) {
		model.addAttribute("message", "Login failed!");
		return "forward:/nextgen.com/login";
	}

	@RequestMapping("/nextgen.com/logout/success")
	public String logout(Model model) {
		model.addAttribute("message", "Logout successful!");
		return "forward:/nextgen.com/login";
	}

	@RequestMapping("nextgen.com/access/denied")
	public String access(Model model) {
		model.addAttribute("message", "Access denied!");
		return "forward:/nextgen.com/login";
	}

}
