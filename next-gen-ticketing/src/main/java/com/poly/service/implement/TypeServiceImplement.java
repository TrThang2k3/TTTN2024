package com.poly.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.entity.Type;
import com.poly.repository.TypeDAO;
import com.poly.service.TypeService;

@Service
public class TypeServiceImplement implements TypeService{
	@Autowired
	TypeDAO dao;

	@Override
	public List<Type> findAll() {
		return dao.findAll();
	}

	@Override
	public Type findById(Integer id) {
		return dao.findById(id).get();
	}

	@Override
	public Type create(Type type) {
		return dao.save(type);
	}

	@Override
	public Type update(Type type) {
		return dao.save(type);
	}

	@Override
	public void delete(Integer id) {
		dao.deleteById(id);
	}

}
