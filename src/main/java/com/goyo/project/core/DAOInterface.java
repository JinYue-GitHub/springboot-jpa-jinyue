/*package com.goyo.project.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;
*//**
 * 通用DAO接口
 * 
 *//*
@NoRepositoryBean
@Component
public interface DAOInterface<T,ID> extends JpaRepository<T, ID> {
}

@NoRepositoryBean
public interface DAOInterface<T> extends CrudRepository<T,Long> {
	@Transactional
    @Modifying
    @Query(value="comment on column :className.:fieldName is :comment", nativeQuery = true)
	public void executeSql(@Param("className")String className,@Param("fieldName")String fieldName,@Param("comment")String comment);
}
*/