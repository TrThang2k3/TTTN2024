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
import com.poly.entity.Nft;
import com.poly.service.NftService;

@CrossOrigin("*")
@RestController
@RequestMapping("/nextgen.com/rest/nfts")
public class NftRestController {
	@Autowired
	NftService service;
	
	@GetMapping()
	public List<Nft> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Nft getOne(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@PostMapping()
	public Nft create(@RequestBody JsonNode nft) {
		return service.create(nft);
	}

	@PutMapping("/{id}")
	public Nft update(@PathVariable("id") Integer id, @RequestBody Nft nft) {
		return service.update(nft);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}
}
