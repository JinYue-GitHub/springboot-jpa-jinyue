/*package com.goyo.project.util;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import com.goyo.project.core.DAOInterface;
@Component
public class MyRepositoryImpl<T, ID extends Serializable>
  extends SimpleJpaRepository<T, ID> 
	implements DAOInterface<T,ID>
{
	public MyRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		
		super(entityInformation, entityManager);
	}
	

}*/