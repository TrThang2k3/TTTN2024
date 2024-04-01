package com.poly.controllerUser;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.DTO.AccountDTO;
import com.poly.entity.Account;
import com.poly.entity.Publisher;
import com.poly.service.AccountService;
import com.poly.service.PublisherService;
import com.poly.service.TicketService;
import com.poly.util.service.SessionService;

@Controller
public class UserController {
	@Autowired
	SessionService sessionService;
	@Autowired
	AccountService accountService;
	@Autowired
	PublisherService publisherService;
	@Autowired
	TicketService ticketService;
	
	@GetMapping({"/nextgen.com", "/nextgen.com/home"})
	public String index() {
		return "/template-user/home";
	}
	
	@GetMapping("/nextgen.com/ticket-gallery")
	public String ticket(Model model) {
		model.addAttribute("tickets", ticketService.findAll());
		return "/template-user/ticket";
	}
	
	@GetMapping("/nextgen.com/account/profile")
	public String profile(Model model) {
		Account account = accountService.findById(getLogAcc().getId());
		model.addAttribute("account", account);
		
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId());
		dto.setFirstName(account.getFirstName());
		dto.setLastName(account.getLastName());
		dto.setEmail(account.getEmail());
		dto.setPhone(account.getPhone());		
		model.addAttribute("accountDto", dto);
		
		return "/template-user/profile";
	}
	
	@GetMapping("/nextgen.com/account/payment")
	public String newTicket() {
		return "/template-user/payment";
	}
	
	@GetMapping("/nextgen.com/signup")
	public String register(Model model) {
		Account account = new Account();
		model.addAttribute("account", account);
		return "/template-user/register";
	}	

	@CrossOrigin("*")
	@ResponseBody
	@RequestMapping("/nextgen.com/rest/authentication")
	public Object getAuthentication() {
		return sessionService.get("authentication");
	}
	
	public Account getLogAcc() {
		Map<String, Object> authentication = new HashMap<>();
		authentication = sessionService.get("authentication");
		Object auth = authentication.get("account");
		Account account = (Account) auth;
		return account;
	}
	
	
}
