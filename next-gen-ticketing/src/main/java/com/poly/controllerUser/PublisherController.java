package com.poly.controllerUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublisherController {
	@GetMapping("/nextgen.com/publisher/new-ticket")
	public String newTicket() {
		return "/template-user/publisher";
	}
}
