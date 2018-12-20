package com.goyo.project.common;

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
import com.goyo.project.model.DocumentIssued;

public class CommonDataForDocumentIssued {

	//*********************************************文件-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<DocumentIssued,String> baserepository;

	public CommonDataForDocumentIssued() {
		super();
	}

	/**
	 * 获取DocumentIssued单个对象
	 * @param documentIssued
	 * @return Result<DocumentIssued>
	 */
	protected Result<DocumentIssued> getDocumentIssuedSingle(DocumentIssued documentIssued) {
		return ResultGenerator.genSuccessResult(baserepository.findById(documentIssued, documentIssued.getId()));
	}

	/**
	 * 获取文件分页对象
	 * @param documentIssued
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<DocumentIssued>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<DocumentIssued>> getDocumentIssuedPage(DocumentIssued documentIssued, String pageNum, String pageSize) {
		Page<DocumentIssued> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<DocumentIssued>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<DocumentIssued> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate subjectionp = null;
	                Predicate organizationp = null;
	                String subjection = documentIssued.getSubjection();
	                String organization = documentIssued.getOrganization();
					if(null != documentIssued && !StringUtils.isEmpty(subjection)) {
	                	subjectionp = cb.like(root.<String> get("subjection"), "%" + subjection + "%");
	                }
					if(null != documentIssued && !StringUtils.isEmpty(organization)) {
						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
	                }
	                 if(null != subjectionp) query.where(subjectionp);
	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, DocumentIssued.class);
//				long totalElements = page.getTotalElements();//总行数
//				int totalPages = page.getTotalPages();//总页数
//				List<DocumentIssued> content = page.getContent();//分页结果集
//				for(DocumentIssued x:content){
//					System.out.println(x.getId());
//				}
		}else if(StringUtils.isBlank(pageNum)&&StringUtils.isBlank(pageSize)){
			page = baserepository.findAll(pageable,DocumentIssued.class);
		}else{
			return new Result<Page<DocumentIssued>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 获取文件分页对象
	 * @param documentIssued
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<DocumentIssued>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<DocumentIssued>> getDocumentIssuedPage(DocumentIssued documentIssued, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<DocumentIssued> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<DocumentIssued>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<DocumentIssued> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate subjectionp = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String subjection = documentIssued.getSubjection();
	                String organization = documentIssued.getOrganization();
					if(StringUtils.isNotEmpty(subjection)) {
						subjectionp = cb.like(root.<String> get("subjection"), "%" + subjection + "%");
	                }
					if(StringUtils.isNotEmpty(organization)) {
						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
					if(Optional.ofNullable(subjectionp).isPresent()) query.where(subjectionp);
	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable, DocumentIssued.class);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<DocumentIssued>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<DocumentIssued> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate subjectionp = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String subjection = documentIssued.getSubjection();
	                String organization = documentIssued.getOrganization();
	                if(StringUtils.isNotEmpty(subjection)) {
	                	subjectionp = cb.like(root.<String> get("subjection"), "%" + subjection + "%");
	                }
					if(StringUtils.isNotEmpty(organization)) {
						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
					if(Optional.ofNullable(subjectionp).isPresent()) query.where(subjectionp);
	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable,DocumentIssued.class);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,DocumentIssued.class);
		}else{
			return new Result<Page<DocumentIssued>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 文件添加
	 * @param documentIssued
	 * @return Result<String>
	 */
	protected Result<String> getDocumentIssuedAdd(DocumentIssued documentIssued) {
		try {
			baserepository.save(documentIssued);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	/**
	 * 文件修改
	 * @param documentIssued
	 * @return Result<String>
	 */
	protected Result<String> getDocumentIssuedUpdate(DocumentIssued documentIssued) {
		try {
			baserepository.update(documentIssued);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文件删除
	 * @param documentIssued
	 * @return Result<String>
	 */
	protected Result<String> getDocumentIssuedDel(DocumentIssued documentIssued) {
		try {
			baserepository.delete(documentIssued);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	

}