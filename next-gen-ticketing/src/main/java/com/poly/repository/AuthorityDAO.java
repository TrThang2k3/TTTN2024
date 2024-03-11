package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Account;
import com.poly.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{

	List<Authority> findByAccount(Account account);

}
