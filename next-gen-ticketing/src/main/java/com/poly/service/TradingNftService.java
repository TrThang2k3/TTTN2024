package com.poly.service;

import java.util.List;

import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.TradingNft;

public interface TradingNftService {

	List<TradingNft> findAll();

	TradingNft findById(Integer id);

	TradingNft create(TradingNft entry);

	TradingNft update(TradingNft entry);

	void deleteById(Integer id);
	
	List<TradingNft> findAvailableTradingByAccount(Account account);
	
	Boolean existsAvailableNftTrading(Nft nft);

	TradingNft findAvailableTradingNft(Integer nftId);
	
}
