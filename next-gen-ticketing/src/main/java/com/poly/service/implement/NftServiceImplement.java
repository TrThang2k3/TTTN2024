package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.Ticket;
import com.poly.repository.NftDAO;
import com.poly.service.NftService;

@Service
public class NftServiceImplement implements NftService{
	@Autowired
	NftDAO dao;

	@Override
	public List<Nft> findAll() {
		return dao.findAll();
	}

	@Override
	public Nft findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Nft create(JsonNode nftData) {
		ObjectMapper mapper = new ObjectMapper();
		Nft nft = mapper.convertValue(nftData, Nft.class);
		return dao.save(nft);
	}

	@Override
	public Nft update(Nft nft) {
		return dao.save(nft);
	}

	@Override
	public List<Nft> findByAccount(Account account) {
		return dao.findByAccount(account);
	}

	@Override
	public List<Nft> findByTicket(Ticket ticket) {
		return dao.findByTicket(ticket);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}
	
}
