package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poly.entity.Account;
import com.poly.entity.Invoice;
import com.poly.repository.InvoiceDAO;
import com.poly.service.InvoiceService;

@Service
public class InvoiceServiceImplement implements InvoiceService{
	@Autowired
	InvoiceDAO dao;

	@Override
	public List<Invoice> findAll() {
		return dao.findAll();
	}

	@Override
	public Invoice create(JsonNode invoiceData) {
		ObjectMapper mapper = new ObjectMapper();
		Invoice invoice = mapper.convertValue(invoiceData, Invoice.class);
		return dao.save(invoice);
	}

	@Override
	public Invoice update(Invoice invoice) {
		return dao.save(invoice);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Invoice findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public List<Invoice> findBySeller(Account seller) {
		return dao.findBySeller(seller);
	}

	@Override
	public List<Invoice> findByBuyer(Account buyer) {
		return dao.findByBuyer(buyer);
	}
}
