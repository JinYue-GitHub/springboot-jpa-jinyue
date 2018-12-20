/*package com.goyo.project.util;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends
        JpaRepositoryFactoryBean<R, T, I> {

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new MyRepositoryFactory(em);
    }

    private static class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private final EntityManager em;

        public MyRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }

//        @Override
//        protected Object getTargetRepository(RepositoryMetadata metadata) {
//            return new BaseRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), em);
//        }
        
//        @Override
//        protected Object getTargetRepository(RepositoryInformation information) {
//        	// TODO Auto-generated method stub
//        	return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
//        }
        @Override
        protected <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(
        		RepositoryInformation information, EntityManager entityManager) {
        	// TODO Auto-generated method stub
        	return super.getTargetRepository(information, em);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}*/