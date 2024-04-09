package com.poly.controllerAdmin;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poly.entity.Type;
import com.poly.entity.Ticket;
import com.poly.repository.TicketDAO;
import com.poly.service.TicketService;
import com.poly.util.service.UploadService;
@Controller
@RequestMapping("/nextgen.com/")
public class TicketAdminController {
	@Autowired
	TicketDAO ticketdao;
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	UploadService uploadserivce;
	
	@GetMapping("admin-view-ticket")
	public String viewticket(Model model) {
		model.addAttribute("tickets", ticketdao.findAll());
		model.addAttribute("ticket", new Ticket());
		return "Admin_view/Ticket";
	}
	
	@ModelAttribute("types")
	public List<Type> getTypes() {
	    return ticketService.findAllTypes();
	}
	
	@GetMapping("/admin-view-ticket/edit/{id}")
	public String showEditFrom(@PathVariable("id") Integer id, Model model) {
		Ticket ticket = ticketService.findById(id);
		model.addAttribute("ticket", ticket);
		model.addAttribute("tickets", ticketdao.findAll());
		return"Admin_view/Ticket";
	}
	
	@PostMapping("admin-view-ticket/create")
	public String createTicket(@ModelAttribute Ticket ticket, @RequestParam("imageFile") MultipartFile imageFiles) {
		File imageFile = uploadserivce.save(imageFiles, "img");
		String imageName = imageFile.getName();
		ticket.setImage(imageName);
				
		ticketService.create(ticket);
		return"redirect:/nextgen.com/admin-view-ticket";
	}
	
	@PostMapping("admin-view-ticket/update/{id}")
	public String updateTicket(@PathVariable("id") Integer id, @ModelAttribute Ticket updateTicket, @RequestParam("imageFile") MultipartFile imageFiles) {
		if(!imageFiles.isEmpty()){
			File imageFile = uploadserivce.save(imageFiles, "img");
			String imageName = imageFile.getName();
			updateTicket.setImage(imageName);
		}else{
			Ticket existingTicket=ticketService.findById(id);
			updateTicket.setImage(existingTicket.getImage());
		}
		updateTicket.setId(id);
		ticketService.update(updateTicket);
		return"redirect:/nextgen.com/admin-view-ticket";
	}
	
	@PostMapping("/admin-view-ticket/delete/{id}")
	public String deleteTicket(@PathVariable("id") Integer id) {
		ticketService.deleteById(id);
		return"redirect:/nextgen.com/admin-view-ticket";
	}
	
	@PostMapping("/admin-view-ticket/reset")
	public String resetForm(Model model) {
		model.addAttribute("ticket", new Ticket());
		model.addAttribute("tickets", ticketdao.findAll());
		return "Admin_view/Ticket";
	}

}
