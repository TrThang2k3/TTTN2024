package com.poly.service;

import java.util.List;

import com.poly.entity.Type;

public interface TypeService {
	List<Type> findAll();
	
	Type findById(Integer id);
	
	Type create(Type type);
	
	Type update(Type type);
	
	void delete(Integer id);
}
