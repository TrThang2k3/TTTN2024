package com.poly.controllerUser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.entity.Account;
import com.poly.entity.Publisher;
import com.poly.entity.Ticket;
import com.poly.entity.Type;
import com.poly.repository.TicketDAO;
import com.poly.service.PublisherService;
import com.poly.service.TicketService;
import com.poly.util.service.SessionService;
import com.poly.util.service.UploadService;

@Controller
public class PublisherController {
    @Autowired
    TicketService ticService;
    
    @Autowired
    TicketDAO ticDAO;
    
    @Autowired
    UploadService upService;
    
    @Autowired
    SessionService sessionService;

	@Autowired
	PublisherService publisherService;
    
    @GetMapping("/nextgen.com/publisher")
    public String show(Model model) {
    	Account account = getLogAcc();
    	model.addAttribute("account", account);
    	Ticket ticket = new Ticket();
    	ticket.setPublisher(account.getPublisher());
    	model.addAttribute("ticket", ticket);
		model.addAttribute("tickets", ticService.findAll());
		model.addAttribute("publisher", account.getPublisher());
    	return "/template-user/publisher";
    }
    
    @PostMapping("/nextgen.com/publisher/submit")
    public String SubmitTicket(@ModelAttribute Ticket ticket,@RequestParam("imageFile") MultipartFile imageFiles) {
    	File imageFile = upService.save(imageFiles, "img");
    	String imageName = imageFile.getName();
    	ticket.setImage(imageName);
    	ticService.create(ticket);
    	return "redirect:/nextgen.com/publisher";
    }
    public Account getLogAcc() {
		Map<String, Object> authentication = new HashMap<>();
		authentication = sessionService.get("authentication");
		Object auth = authentication.get("account");
		Account account = (Account) auth;
		return account;
	}
	@ModelAttribute("types")
	public List<Type> getTypes() {
	    return ticService.findAllTypes();
	}
	@PostMapping("/nextgen.com/update/{id}")
	public String updatePublisher(@PathVariable("id") Integer id, @ModelAttribute Publisher updatePublisher) {
		updatePublisher.setId(id);
		publisherService.update(updatePublisher);
		return"redirect:/nextgen.com/publisher";
	}
	
	// @GetMapping("/nextgen.com/publisher")
	// public String test(Model model) {
	// 	Account account=getLogAcc();
	// 	model.addAttribute("tickets", ticService.findAll());
	// 	model.addAttribute("publisher", account.getPublisher());
	// 	return "/template-user/publisher";
	// }
	
	
	
   
}
