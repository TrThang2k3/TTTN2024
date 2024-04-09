package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Account;
import com.poly.entity.Authority;
import com.poly.repository.AuthorityDAO;
import com.poly.repository.RoleDAO;
import com.poly.service.AuthorityService;

@Service
public class AuthorityServiceImplement implements AuthorityService{
	@Autowired
	AuthorityDAO dao;
	@Autowired
	RoleDAO roleDao;

	@Override
	public List<Authority> findByAccount(Account account) {
		return dao.findByAccount(account);
	}

	@Override
	public Authority create(Account account, String roleName) {
		Authority auth = new Authority();
		auth.setAccount(account);
		auth.setRole(roleDao.findById(roleName).get());
		return dao.save(auth);
	}

	@Override
	public List<Authority> findAll() {
		return dao.findAll();
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Integer findId(Integer accountId, String role) {
		return dao.findId(accountId, role);
	}

}
