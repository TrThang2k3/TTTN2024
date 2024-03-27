package com.poly.controllerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Publisher;
import com.poly.repository.PublisherDAO;
import com.poly.service.PublisherService;

@Controller
@RequestMapping("/nextgen.com/")
public class publisherAdminController {
	
	@Autowired
	PublisherService publisherService;
	
	@Autowired
	PublisherDAO publisherDAO;
	
	@GetMapping("admin-view-publisher")
	public String showView(Model model) {
		model.addAttribute("publishers", publisherDAO.findAll());
		model.addAttribute("publisher", new Publisher());
		return"Admin_view/publisher";
	}
	
	@GetMapping("/admin-view-publisher/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model) {
		Publisher publisher = publisherService.findById(id);
		model.addAttribute("publisher", publisher);
		model.addAttribute("publishers", publisherDAO.findAll());
		return "Admin_view/publisher";
	}
	
	@PostMapping("/admin-view-publisher/create")
	public String createPublisher(@ModelAttribute Publisher publisher) {
		publisherService.create(publisher);
		return"redirect:/nextgen.com/admin-view-publisher";
	}
	
	@PostMapping("/admin-view-publisher/update/{id}")
	public String updatePublisher(@PathVariable("id") Integer id, @ModelAttribute Publisher updatePublisher) {
		updatePublisher.setId(id);
		publisherService.update(updatePublisher);
		return"redirect:/nextgen.com/admin-view-publisher";
	}
	
	@PostMapping("/admin-view-publisher/delete/{id}")
	public String deletePublisher(@PathVariable("id") Integer id) {
		publisherService.deleteById(id);
		return"redirect:/nextgen.com/admin-view-publisher";
	}
	@PostMapping("/admin-view-publisher/reset")
	public String resetForm(Model model) {
		model.addAttribute("publisher", new Publisher());
		model.addAttribute("publishers", publisherDAO.findAll());
		return"Admin_view/Publisher";
	}
}
