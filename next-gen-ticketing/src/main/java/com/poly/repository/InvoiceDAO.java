package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Account;
import com.poly.entity.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer>{

	List<Invoice> findByAccount(Account account);

}
