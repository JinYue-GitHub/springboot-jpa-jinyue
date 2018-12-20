package com.goyo.project.data.dictionary.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.goyo.project.data.dictionary.dao.DataDictionaryGroupsDao;
import com.goyo.project.data.dictionary.service.DataDictionaryGroupsService;
import com.goyo.project.model.DataDictionaryGroups;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class DataDictionaryGroupsServiceImpl implements DataDictionaryGroupsService {
    @Resource
    private DataDictionaryGroupsDao dataDictionaryGroupsDao;
    @Override
    public DataDictionaryGroups findByGroupName(String username) {
        return dataDictionaryGroupsDao.findByGroupName(username);
    }
	@Override
	public DataDictionaryGroups findById(String groupCode) {
		return dataDictionaryGroupsDao.findById(groupCode).get();
	}
	@Override
	public void save(DataDictionaryGroups dataDictionaryGroups) {
		dataDictionaryGroupsDao.save(dataDictionaryGroups);		
	}
	@Override
	public void del(String groupCode) {
		dataDictionaryGroupsDao.deleteById(groupCode);
	}
	@Override
	public Page<DataDictionaryGroups> findAll(DataDictionaryGroups dataDictionaryGroups,Pageable pageable) {
//		sex age organization fipost post qualification  name
		Page<DataDictionaryGroups> page = null;
			page = dataDictionaryGroupsDao.findAll(new Specification<DataDictionaryGroups>() {
				/**
				 * 序列化id
				 */
				private static final long serialVersionUID = 9041816359827790396L;

				@Override
	            public Predicate toPredicate(Root<DataDictionaryGroups> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate groupNamep = null;
	                String groupName = dataDictionaryGroups.getGroupName();
					if(StringUtils.isNotEmpty(groupName)) {
						groupNamep = cb.like(root.<String> get("groupName"), "%" + groupName + "%");
	                }
	                 if(Optional.ofNullable(groupNamep).isPresent()) query.where(groupNamep);
	                 return null;
	             }
	         }, pageable);
		
		return page;
	}
	@Override
	public Page<DataDictionaryGroups> findAll(Pageable pageable) {
		return dataDictionaryGroupsDao.findAll(pageable);
	}
}