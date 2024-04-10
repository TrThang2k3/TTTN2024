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
import com.poly.entity.TradingNft;
import com.poly.service.TradingNftService;

@CrossOrigin("*")
@RestController
@RequestMapping("/nextgen.com/rest/trading-nfts")
public class TradingNftRestController {
	@Autowired
	TradingNftService service;
	
	@GetMapping()
	public List<TradingNft> getAll() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public TradingNft getOne(@PathVariable("id") Integer id) {
		return service.findById(id);
	}

	@PostMapping()
	public TradingNft create(TradingNft tradingNft) {
		return service.create(tradingNft);
	}

	@PutMapping("/{id}")
	public TradingNft update(@PathVariable("id") Integer id, @RequestBody TradingNft tradingNft) {
		return service.update(tradingNft);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable("id") Integer id) {
		service.deleteById(id);
	}
}
