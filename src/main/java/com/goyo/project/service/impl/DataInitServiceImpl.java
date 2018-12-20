package com.goyo.project.service.impl;

import org.springframework.stereotype.Service;
import com.goyo.project.service.DataInitService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class DataInitServiceImpl implements DataInitService {
	@PersistenceContext
    private EntityManager em;
	@Override
	public void executeUpdate(String sql) {
		em.createNativeQuery(sql).executeUpdate();
	}
}

