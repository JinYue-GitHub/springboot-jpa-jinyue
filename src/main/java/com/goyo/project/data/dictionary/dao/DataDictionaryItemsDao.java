package com.goyo.project.data.dictionary.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.goyo.project.model.DataDictionaryItems;

public interface DataDictionaryItemsDao extends JpaRepository<DataDictionaryItems,String>,JpaSpecificationExecutor<DataDictionaryItems>{}