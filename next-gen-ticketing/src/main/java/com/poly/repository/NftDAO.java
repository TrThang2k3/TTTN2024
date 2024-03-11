package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.Ticket;

public interface NftDAO extends JpaRepository<Nft, Integer>{

	List<Nft> findByAccount(Account account);

	List<Nft> findByTicket(Ticket ticket);

}
