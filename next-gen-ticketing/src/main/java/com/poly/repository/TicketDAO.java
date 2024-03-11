package com.poly.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Publisher;
import com.poly.entity.Ticket;
import com.poly.entity.Type;

public interface TicketDAO extends JpaRepository<Ticket, Integer>{

	List<Ticket> findByPublisher(Publisher publisher);

	List<Ticket> findByType(Type type);

}
