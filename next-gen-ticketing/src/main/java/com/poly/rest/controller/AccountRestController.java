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

import com.poly.entity.Account;
import com.poly.service.AccountService;

@CrossOrigin("*")
@RestController
@RequestMapping("/nextgen.com/rest/accounts")
public class AccountRestController {
	@Autowired
	AccountService service;

	@GetMapping()
	public List<Account> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Account getOne(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@PostMapping()
	public Account create(@RequestBody Account account) {
		return service.create(account);
	}

	@PutMapping("/{id}")
	public Account update(@PathVariable("id") Integer id, @RequestBody Account account) {
		return service.update(account);
	}
	
	@PutMapping("/{id}/wallet-address")
	public Account updateWalletAddress(@PathVariable("id") Integer id, @RequestBody String walletAddress) {
		return service.update(id, walletAddress);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}
}
