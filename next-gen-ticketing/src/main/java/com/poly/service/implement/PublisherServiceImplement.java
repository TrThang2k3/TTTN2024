package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Publisher;
import com.poly.repository.PublisherDAO;
import com.poly.service.PublisherService;

@Service
public class PublisherServiceImplement implements PublisherService{
	@Autowired
	PublisherDAO dao;

	@Override
	public List<Publisher> findAll() {
		return dao.findAll();
	}

	@Override
	public Publisher findById(Integer id) {
		return dao.getReferenceById(id);
	}

	@Override
	public Publisher create(Publisher publisher) {
		return dao.save(publisher);
	}

	@Override
	public Publisher update(Publisher publisher) {
		return dao.save(publisher);
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

}
