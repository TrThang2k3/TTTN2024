package com.poly.service;

import java.util.List;

import com.poly.entity.Account;
import com.poly.entity.Authority;

public interface AuthorityService {
	List<Authority> findByAccount(Account account);
	
	Authority create(Account acc, String role);

	List<Authority> findAll();

	void deleteById(Integer id);
}
