package com.poly.service;

import java.util.List;

import com.poly.entity.Publisher;
import com.poly.entity.Ticket;
import com.poly.entity.Type;

public interface TicketService {
	List<Ticket> findAll();
	
	Ticket findById(Integer id);
	
	Ticket create(Ticket ticket);
	
	Ticket update(Ticket ticket);
	
	void deleteById(Integer id);
	
	List<Ticket> findByPublisher(Publisher publisher);
	
	List<Ticket> findByType(Type type);
	
	List<Type> findAllTypes();
}
