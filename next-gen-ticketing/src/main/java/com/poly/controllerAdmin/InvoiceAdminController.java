package com.poly.controllerAdmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.entity.Invoice;
import com.poly.repository.InvoiceDAO;
import com.poly.service.AccountService;
import com.poly.service.InvoiceService;

@Controller
@RequestMapping("/nextgen.com/")
public class InvoiceAdminController {
	@Autowired
	InvoiceDAO invoiceDAO;
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	AccountService accountservice;
	@GetMapping("admin-view-invoice")
	public String showView(Model model) {
		model.addAttribute("invoices", invoiceDAO.findAll());
		model.addAttribute("accountList", accountservice.findAll());
		model.addAttribute("invoice", new Invoice());
		return "Admin_view/Invoice";
	}
	
	@GetMapping("/admin-view-invoice/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model) {
		Invoice invoice = invoiceService.findById(id);
		model.addAttribute("invoice", invoice);
		model.addAttribute("invoices", invoiceDAO.findAll());
		return"Admin_view/Invoice";
	}
	
	@PostMapping("/admin-view-invoice/create")
	public String createInvoice(@ModelAttribute Invoice invoice) {
		invoiceService.create(invoice);
		return"redirect:/nextgen.com/admin-view-invoice";
	}
	
	@PostMapping("/admin-view-invoice/update/{id}")
	public String updateInvoice(@PathVariable("id") Integer id, @ModelAttribute Invoice updateInvoice) {
		updateInvoice.setId(id);
		invoiceService.update(updateInvoice);
		return"redirect:/nextgen.com/admin-view-invoice";
	}
	
	@PostMapping("/admin-view-invoice/delete/{id}")
	public String deleteInvoice(@PathVariable("id") Integer id) {
		invoiceService.deleteById(id);
		return"redirect:/nextgen.com/admin-view-invoice";
	}
	
	@PostMapping("/admin-view-invoice/reset")
	public String resetForm(Model model) {
		model.addAttribute("invoice", new Invoice());
		model.addAttribute("invoices", invoiceDAO.findAll());
		model.addAttribute("accountList", accountservice.findAll());
		return"Admin_view/Invoice";
	}
}
