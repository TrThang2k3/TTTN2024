package com.poly.service;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.Ticket;

public interface NftService {
	List<Nft> findAll();
	
	Nft findById(Integer id);
	
	Nft create(JsonNode nftData);
	
	Nft update(Nft nft);
	
	List<Nft> findByAccount(Account account);
	
	List<Nft> findByTicket(Ticket ticket);

	void deleteById(Integer id);

	List<Nft> getNotExpiredNftsByAccount(Account account);
}
