package com.poly.service;

import java.util.List;

import com.poly.entity.Account;

public interface AccountService {
	List<Account> findAll();

	Account findById(Integer id);
	
	Account findByEmail(String email);

	Account create(Account account);

	Account update(Account account);

	void deleteById(Integer id);
	
	Boolean existByEmail(String email);
}
