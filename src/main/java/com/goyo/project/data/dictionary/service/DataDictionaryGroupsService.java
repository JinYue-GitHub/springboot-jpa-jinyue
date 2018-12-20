package com.goyo.project.data.dictionary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.goyo.project.model.DataDictionaryGroups;
@Transactional
public interface DataDictionaryGroupsService {
    /**通过groupName查找字典组信息;*/
	public DataDictionaryGroups findByGroupName(String groupName);
    //查询
    public DataDictionaryGroups findById(String groupCode);
    public Page<DataDictionaryGroups> findAll(Pageable pageable);
    public Page<DataDictionaryGroups> findAll(DataDictionaryGroups dataDictionaryGroups,Pageable pageable);
    //添加
    public void save(DataDictionaryGroups dataDictionaryGroups);
    //删除
    public void del(String groupCode);
    
   
}