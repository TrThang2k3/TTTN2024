package com.poly.service.implement;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Account;
import com.poly.repository.AccountDAO;
import com.poly.service.AccountService;

@Service
public class AccountServiceImplement implements AccountService{
	@Autowired
	AccountDAO dao;
	
	@Override
	public List<Account> findAll() {
		return dao.findAll();
	}

	@Override
	public Account findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Account findByEmail(String email) {
		return dao.findByEmail(email);
	}

	@Override
	public Account create(Account account) {
		if (dao.existsByEmail(account.getEmail())) {
			throw new RuntimeException("Duplicated by email");
		}
		return dao.save(account);
	}
	

	@Override
	public Account update(Account account) {
		if (!dao.existsById(account.getId())) {
			throw new RuntimeException("Not existed");
		}
		return dao.save(account);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Boolean existByEmail(String email) {
		return dao.existsByEmail(email);
	}

	@Override
	public Account update(Integer id, String walletAddress) {
		Account account = dao.findById(id).get();
		account.setWalletAddress(walletAddress);
		return dao.save(account);
	}

}
