package com.poly.service;

import java.util.List;

import com.poly.entity.Account;
import com.poly.entity.Publisher;

public interface PublisherService {
	List<Publisher> findAll();
	
	Publisher findById(Integer id);
	
	Publisher create(Publisher publisher);
	
	Publisher update(Publisher publisher);
	
	void deleteById(Integer id);

	Publisher findByAccount(Account account);
}
