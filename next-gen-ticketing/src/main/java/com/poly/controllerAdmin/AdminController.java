package com.poly.controllerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.poly.service.AuthorityService;

@Controller
@RequestMapping("/nextgen.com/")
public class AdminController {

	@Autowired
	AuthorityService authorityService;

	@Autowired
	AccountService accountservice;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@GetMapping("admin-view")
	public String view(Model model) {
		model.addAttribute("accountList", accountservice.findAll());
		model.addAttribute("account", new Account());
		return "Admin_view/Account";
	}
	
	@GetMapping("/admin-view/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model) {
		Account account = accountservice.findById(id);
		model.addAttribute("account", account);
		model.addAttribute("accountList", accountservice.findAll());
		return "Admin_view/Account";
	}

	@PostMapping("/admin-view/create")
	public String createAccount(@ModelAttribute Account account) {
		String password = "123@123";
		account.setPassword(passwordEncoder.encode(password));
		accountservice.create(account);
		authorityService.create(account, "Customer");
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
		try {
			Integer authId = authorityService.findId(id, "Customer");
			authorityService.deleteById(authId);
			accountservice.deleteById(id);
		} catch (Exception e) {
			authorityService.create(accountservice.findById(id), "Customer");
		}
		return "redirect:/nextgen.com/admin-view";
	}
	
	@PostMapping("/admin-view/reset")
	public String resetForm(Model model) {
		model.addAttribute("account", new Account());
		model.addAttribute("accountList", accountservice.findAll());
		return "Admin_view/Account";
	}
	
}
