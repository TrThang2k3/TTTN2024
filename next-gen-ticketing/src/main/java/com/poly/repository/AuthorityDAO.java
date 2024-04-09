package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.Account;
import com.poly.entity.Authority;

public interface AuthorityDAO extends JpaRepository<Authority, Integer>{

	List<Authority> findByAccount(Account account);

	@Query(value = "SELECT o.id FROM Authority o WHERE o.account.id = ?1 AND o.role.name = ?2")
	Integer findId(Integer accountId, String role);

}
