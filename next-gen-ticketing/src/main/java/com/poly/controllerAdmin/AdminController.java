package com.poly.controllerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.poly.entity.Account;
import com.poly.entity.Ticket;
import com.poly.repository.AccountDAO;
import com.poly.repository.TicketDAO;
import com.poly.service.AccountService;

@Controller
@RequestMapping("/nextgen.com/")
public class AdminController {

	@Autowired
	AccountDAO dao;

	@Autowired
	TicketDAO ticketdao;

	@Autowired
	AccountService accountservice;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@GetMapping("admin-view")
	public String view(Model model) {
		model.addAttribute("accountList", dao.findAll());
		model.addAttribute("account", new Account());
		return "Admin_view/Account";
	}
	
	@GetMapping("/admin-view/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model) {
		Account account = accountservice.findById(id);
		model.addAttribute("account", account);
		model.addAttribute("accountList", dao.findAll());
		return "Admin_view/Account";
	}

	@PostMapping("/admin-view/create")
	public String createAccount(@ModelAttribute Account account) {
		String password = account.getPassword();
		account.setPassword(passwordEncoder.encode(password));
		accountservice.create(account);
		return "redirect:/nextgen.com/admin-view";
	}
	
	@PostMapping("/admin-view/update/{id}")
	public String updateAccount(@PathVariable("id") Integer id, @ModelAttribute Account updateAccount) {
		updateAccount.setId(id);
		accountservice.update(updateAccount);
		return "redirect:/nextgen.com/admin-view";
	}
	
	@PostMapping("/admin-view/delete/{id}")
	public String deleteAccount(@PathVariable("id") Integer id) {
		accountservice.deleteById(id);
		return "redirect:/nextgen.com/admin-view";
	}
	
	@PostMapping("/admin-view/reset")
	public String resetForm(Model model) {
		model.addAttribute("account", new Account());
		model.addAttribute("accountList", dao.findAll());
		return "Admin_view/Account";
	}
	
}
