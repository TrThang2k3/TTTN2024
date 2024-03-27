package com.poly.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Invoice;
import com.poly.service.InvoiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/nextgen.com/rest/invoices")
public class InvoiceRestController {
	@Autowired
	InvoiceService service;
	
	@GetMapping()
	public List<Invoice> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Invoice getOne(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@PostMapping()
	public Invoice create(@RequestBody JsonNode invoice) {
		return service.create(invoice);
	}

	@PutMapping("/{id}")
	public Invoice update(@PathVariable("id") Integer id, @RequestBody Invoice invoice) {
		return service.update(invoice);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}
}
