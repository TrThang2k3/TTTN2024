package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.Ticket;

public interface NftDAO extends JpaRepository<Nft, Integer>{

	List<Nft> findByAccount(Account account);

	List<Nft> findByTicket(Ticket ticket);

	@Query(value = "SELECT o FROM Nft o WHERE o.account = ?1 AND o.isExpired = false")
	List<Nft> findNotExpiredNftsByAccount(Account account);

}
