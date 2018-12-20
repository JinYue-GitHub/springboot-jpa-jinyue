package com.goyo.project.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Optional;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.PetitionReportRelevantInformation;
import com.goyo.project.petition.report.dao.PetitionReportRelevantInformationDao;

public class CommonDataForPetitionReportRelevantInformation {

	//*********************************************信访举报信息相关-公共代码************************************************
	/**
	 * 注入仓库
	 */
	
	@Resource
	private BaseRepository<PetitionReportRelevantInformation,String> baserepository;
	
	@Resource
	private PetitionReportRelevantInformationDao petitionReportRelevantInformationDao;

	public CommonDataForPetitionReportRelevantInformation() {
		super();
	}

	/**
	 * 获取PetitionReportRelevantInformation单个对象
	 * @param petitionReportRelevantInformation
	 * @return Result<PetitionReportRelevantInformation>
	 */
	protected Result<PetitionReportRelevantInformation> getPetitionReportRelevantInformationSingle(PetitionReportRelevantInformation petitionReportRelevantInformation) {
		return ResultGenerator.genSuccessResult(baserepository.findById(petitionReportRelevantInformation, petitionReportRelevantInformation.getId()));
	}

	/**
	 * 获取信访举报信息相关分页对象
	 * @param petitionReportRelevantInformation
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<PetitionReportRelevantInformation>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<PetitionReportRelevantInformation>> getPetitionReportRelevantInformationPage(PetitionReportRelevantInformation petitionReportRelevantInformation, String pageNum, String pageSize) {
		Page<PetitionReportRelevantInformation> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(petitionReportRelevantInformation.getPageNum())&&StringUtils.isNoneBlank(petitionReportRelevantInformation.getPageSize())){
			int pageNumInt = Integer.parseInt(petitionReportRelevantInformation.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(petitionReportRelevantInformation.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = petitionReportRelevantInformationDao.findAll(new Specification<PetitionReportRelevantInformation>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<PetitionReportRelevantInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate typeNamep = null;
	                Predicate organizationp = null;
	                String typeName = petitionReportRelevantInformation.getTypeName();
	                try {
	                	if(StringUtils.isNoneBlank(typeName)){
	                		typeName = URLDecoder.decode(typeName, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = petitionReportRelevantInformation.getOrganization();
					if(null != petitionReportRelevantInformation && !StringUtils.isEmpty(typeName)) {
						typeNamep = cb.like(root.<String> get("typeName"), "%" + typeName + "%");
	                }
//					if(null != petitionReportRelevantInformation && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
	                 if(null != typeNamep) query.where(typeNamep);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable);
		}else if(StringUtils.isBlank(petitionReportRelevantInformation.getPageNum())&&StringUtils.isBlank(petitionReportRelevantInformation.getPageSize())){
			page = petitionReportRelevantInformationDao.findAll(new Specification<PetitionReportRelevantInformation>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<PetitionReportRelevantInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate typeNamep = null;
	                String typeName = petitionReportRelevantInformation.getTypeName();
	                try {
	                	if(StringUtils.isNoneBlank(typeName)){
	                		typeName = URLDecoder.decode(typeName, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(null != petitionReportRelevantInformation && !StringUtils.isEmpty(typeName)) {
						typeNamep = cb.like(root.<String> get("typeName"), "%" + typeName + "%");
	                }
	                 if(null != typeNamep) query.where(typeNamep);
	                 return null;
	             }
	         },Pageable.unpaged());
		}else{
			return new Result<Page<PetitionReportRelevantInformation>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 获取信访举报信息相关分页对象
	 * @param petitionReportRelevantInformation
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<PetitionReportRelevantInformation>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<PetitionReportRelevantInformation>> getPetitionReportRelevantInformationPage(PetitionReportRelevantInformation petitionReportRelevantInformation, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<PetitionReportRelevantInformation> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(petitionReportRelevantInformation.getPageNum())&&StringUtils.isNoneBlank(petitionReportRelevantInformation.getPageSize())){
			int pageNumInt = Integer.parseInt(petitionReportRelevantInformation.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(petitionReportRelevantInformation.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<PetitionReportRelevantInformation>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<PetitionReportRelevantInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate typeNamep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String typeName = petitionReportRelevantInformation.getTypeName();
	                try {
	                	if(StringUtils.isNoneBlank(typeName)){
	                		typeName = URLDecoder.decode(typeName, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = petitionReportRelevantInformation.getOrganization();
					if(StringUtils.isNotEmpty(typeName)) {
						typeNamep = cb.like(root.<String> get("typeName"), "%" + typeName + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(typeNamep).isPresent()) query.where(typeNamep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable, PetitionReportRelevantInformation.class);
		}else if((StringUtils.isBlank(petitionReportRelevantInformation.getPageNum())||StringUtils.isBlank(petitionReportRelevantInformation.getPageSize())) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<PetitionReportRelevantInformation>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<PetitionReportRelevantInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate typeNamep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String typeName = petitionReportRelevantInformation.getTypeName();
//	                String organization = petitionReportRelevantInformation.getOrganization();
	                try {
	                	if(StringUtils.isNoneBlank(typeName)){
	                		typeName = URLDecoder.decode(typeName, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(StringUtils.isNotEmpty(typeName)) {
						typeNamep = cb.like(root.<String> get("typeName"), "%" + typeName + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(typeNamep).isPresent()) query.where(typeNamep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable,PetitionReportRelevantInformation.class);
		}else if(StringUtils.isBlank(petitionReportRelevantInformation.getPageNum()) && StringUtils.isBlank(petitionReportRelevantInformation.getPageSize()) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(new Specification<PetitionReportRelevantInformation>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<PetitionReportRelevantInformation> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate typeNamep = null;
	                Predicate time = null;
	                String typeName = petitionReportRelevantInformation.getTypeName();
	                try {
	                	if(StringUtils.isNoneBlank(typeName)){
	                		typeName = URLDecoder.decode(typeName, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(StringUtils.isNotEmpty(typeName)) {
						typeNamep = cb.like(root.<String> get("typeName"), "%" + typeName + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(typeNamep).isPresent()) query.where(typeNamep);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },Pageable.unpaged(),PetitionReportRelevantInformation.class);
		}else{
			return new Result<Page<PetitionReportRelevantInformation>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 信访举报信息相关添加
	 * @param petitionReportRelevantInformation
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportRelevantInformationAdd(PetitionReportRelevantInformation petitionReportRelevantInformation) {
		
		try {
			baserepository.save(petitionReportRelevantInformation);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	/**
	 * 信访举报信息相关修改
	 * @param petitionReportRelevantInformation
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportRelevantInformationUpdate(PetitionReportRelevantInformation petitionReportRelevantInformation) {
		
		try {
			baserepository.update(petitionReportRelevantInformation);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 信访举报信息相关删除
	 * @param petitionReportRelevantInformation
	 * @return Result<String>
	 */
	protected Result<String> getPetitionReportRelevantInformationDel(PetitionReportRelevantInformation petitionReportRelevantInformation) {
		try {
			baserepository.delete(petitionReportRelevantInformation);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	

}