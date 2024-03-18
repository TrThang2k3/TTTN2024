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

import com.poly.entity.Ticket;
import com.poly.service.TicketService;

@CrossOrigin("*")
@RestController
@RequestMapping("/nextgen.com/rest/tickets")
public class TicketRestController {
	@Autowired
	TicketService service;
	
	@GetMapping()
	public List<Ticket> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Ticket getOne(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@PostMapping()
	public Ticket create(@RequestBody Ticket ticket) {
		return service.create(ticket);
	}

	@PutMapping("/{id}")
	public Ticket update(@PathVariable("id") Integer id, @RequestBody Ticket ticket) {
		return service.update(ticket);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}
}
