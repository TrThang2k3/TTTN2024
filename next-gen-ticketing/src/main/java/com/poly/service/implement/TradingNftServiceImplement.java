package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.TradingNft;
import com.poly.repository.TradingNftDAO;
import com.poly.service.TradingNftService;

@Service
public class TradingNftServiceImplement implements TradingNftService{
	@Autowired
	TradingNftDAO dao;

	@Override
	public List<TradingNft> findAll() {
		return dao.findAll();
	}

	@Override
	public TradingNft findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public TradingNft create(TradingNft entry) {
		if(existsAvailableNftTrading(entry.getNft())) {
			throw new RuntimeException("This NFT is already on sale");
		}
		return dao.save(entry);
	}

	@Override
	public TradingNft update(TradingNft entry) {
		if(!dao.existsById(entry.getId())) {
			throw new RuntimeException("Not exist!");
		}
		return dao.save(entry);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<TradingNft> findAvailableTradingByAccount(Account account) {
		return dao.findAvailableTradingByAccount(account);
	}

	@Override
	public Boolean existsAvailableNftTrading(Nft nft) {
		return dao.findAvailableNftTrading(nft) != null ? true : false;
	}

	@Override
	public TradingNft findAvailableTradingNft(Integer nftId) {
		return dao.findAvailableNftTrading(nftId);
	}

	@Override
	public List<TradingNft> findAllAvailable() {
		return dao.findAllAvailable();
	}

}
