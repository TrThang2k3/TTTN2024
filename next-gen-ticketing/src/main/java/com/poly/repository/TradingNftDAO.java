package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.TradingNft;

public interface TradingNftDAO extends JpaRepository<TradingNft, Integer>{
	
	@Query(value = "SELECT o FROM TradingNft o WHERE o.account = ?1 AND o.isAvailable = true")
	List<TradingNft> findAvailableTradingByAccount(Account account);

	@Query(value = "SELECT o FROM TradingNft o WHERE o.nft = ?1 AND o.isAvailable = true")
	TradingNft findAvailableNftTrading(Nft nft);

	@Query(value = "SELECT o FROM TradingNft o WHERE o.nft.id = ?1 AND o.isAvailable = true")
	TradingNft findAvailableNftTrading(Integer nftId);

}
