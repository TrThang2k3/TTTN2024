package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Ticket;

public interface TicketDAO extends JpaRepository<Ticket, Integer>{

}
