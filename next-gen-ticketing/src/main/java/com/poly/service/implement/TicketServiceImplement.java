package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Publisher;
import com.poly.entity.Ticket;
import com.poly.entity.Type;
import com.poly.repository.TicketDAO;
import com.poly.service.TicketService;

@Service
public class TicketServiceImplement implements TicketService{
	@Autowired
	TicketDAO dao;

	@Override
	public List<Ticket> findAll() {
		return dao.findAll();
	}

	@Override
	public Ticket findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Ticket create(Ticket ticket) {
		return dao.save(ticket);
	}

	@Override
	public Ticket update(Ticket ticket) {
		return dao.save(ticket);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public List<Ticket> findByPublisher(Publisher publisher) {
		return dao.findByPublisher(publisher);
	}

	@Override
	public List<Ticket> findByType(Type type) {
		return dao.findByType(type);
	}

}
