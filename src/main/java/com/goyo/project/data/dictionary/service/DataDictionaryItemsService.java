package com.goyo.project.data.dictionary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import com.goyo.project.model.DataDictionaryItems;
@Transactional
public interface DataDictionaryItemsService {
    //查询
    public DataDictionaryItems findById(String dataitemCode);
    public Page<DataDictionaryItems> findAll(Pageable pageable);
    public Page<DataDictionaryItems> findAll(DataDictionaryItems dataDictionaryItems,Pageable pageable);
    //添加
    public void save(DataDictionaryItems dataDictionaryItems);
    //删除
    public void del(String dataitemCode);
    
   
}