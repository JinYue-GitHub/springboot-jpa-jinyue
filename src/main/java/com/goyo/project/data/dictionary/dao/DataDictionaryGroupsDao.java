package com.goyo.project.data.dictionary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.goyo.project.model.DataDictionaryGroups;

public interface DataDictionaryGroupsDao extends JpaRepository<DataDictionaryGroups,String>,JpaSpecificationExecutor<DataDictionaryGroups>{
	/**通过groupName查找字典组信息;*/
    public DataDictionaryGroups findByGroupName(String groupName);
}