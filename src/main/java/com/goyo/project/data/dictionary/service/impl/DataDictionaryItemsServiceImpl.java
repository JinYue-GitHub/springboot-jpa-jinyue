package com.goyo.project.data.dictionary.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.goyo.project.data.dictionary.dao.DataDictionaryItemsDao;
import com.goyo.project.data.dictionary.service.DataDictionaryItemsService;
import com.goyo.project.model.DataDictionaryItems;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class DataDictionaryItemsServiceImpl implements DataDictionaryItemsService {
    @Resource
    private DataDictionaryItemsDao dataDictionaryItemsDao;
	@Override
	public DataDictionaryItems findById(String dataitemCode) {
		return dataDictionaryItemsDao.findById(dataitemCode).get();
	}
	@Override
	public void save(DataDictionaryItems dataDictionaryItems) {
		dataDictionaryItemsDao.save(dataDictionaryItems);		
	}
	@Override
	public void del(String dataitemCode) {
		dataDictionaryItemsDao.deleteById(dataitemCode);
	}
	@Override
	public Page<DataDictionaryItems> findAll(DataDictionaryItems dataDictionaryItems,Pageable pageable) {
//		sex age organization fipost post qualification  name
		Page<DataDictionaryItems> page = null;
			page = dataDictionaryItemsDao.findAll(new Specification<DataDictionaryItems>() {
				/**
				 * 序列化id
				 */
				private static final long serialVersionUID = 9041816359827790396L;

				@Override
	            public Predicate toPredicate(Root<DataDictionaryItems> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate dataitemNamep = null;
	                String dataitemName = dataDictionaryItems.getDataitemName();
					if(StringUtils.isNotEmpty(dataitemName)) {
						dataitemNamep = cb.like(root.<String> get("dataitemName"), "%" + dataitemName + "%");
	                }
	                 if(Optional.ofNullable(dataitemNamep).isPresent()) query.where(dataitemNamep);
	                 return null;
	             }
	         }, pageable);
		
		return page;
	}
	@Override
	public Page<DataDictionaryItems> findAll(Pageable pageable) {
		return dataDictionaryItemsDao.findAll(pageable);
	}
}