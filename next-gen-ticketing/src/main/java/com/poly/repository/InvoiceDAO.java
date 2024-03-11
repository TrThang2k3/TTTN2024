package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Invoice;

public interface InvoiceDAO extends JpaRepository<Invoice, Integer>{

}
