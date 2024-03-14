package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.entity.Account;

public interface AccountDAO extends JpaRepository<Account, Integer>{
	Account findByEmail(String email);

	Boolean existsByEmail(String email);

}
