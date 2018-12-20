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
import com.goyo.project.model.Article;

public class BackCommonDataForArticle {

	//*********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<Article,String> baserepository;

	public BackCommonDataForArticle() {
		super();
	}

	/**
	 * 获取Article单个对象
	 * @param article
	 * @return Result<Article>
	 */
	protected Result<Article> getArticleSingle(Article article) {
		return ResultGenerator.genSuccessResult(baserepository.findById(article, article.getId()));
	}

	/**
	 * 获取文章分页对象
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Article>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Article>> getArticlePage(Article article, String pageNum, String pageSize) {
		Page<Article> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<Article>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate namep = null;
	                Predicate typeIdsp = null;
	                String name = article.getName();
	                String typeIds = article.getTypeIds();
					if(null != article && !StringUtils.isEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(null != article && !StringUtils.isEmpty(typeIds)) {
						typeIdsp = cb.like(root.<String> get("typeIds"), "%" + typeIds + "%");
	                }
	                 if(null != namep) query.where(namep);
	                 if(null != typeIdsp) query.where(typeIdsp);
	                 return null;
	             }
	         }, pageable, Article.class);
		}else if(StringUtils.isBlank(pageNum)&&StringUtils.isBlank(pageSize)){
			page = baserepository.findAll(pageable,Article.class);
		}else{
			return new Result<Page<Article>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 获取文章分页对象
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Article>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Article>> getArticlePage(Article article, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<Article> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<Article>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate namep = null;
	                Predicate typeIdsp = null;
	                Predicate time = null;
	                String name = article.getName();
	                String typeIds = article.getTypeIds();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(StringUtils.isNotEmpty(typeIds)) {
						typeIdsp = cb.like(root.<String> get("typeIds"), "%" + typeIds + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(typeIdsp).isPresent()) query.where(typeIdsp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable, Article.class);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<Article>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate namep = null;
	                Predicate typeIdsp = null;
	                Predicate time = null;
	                String name = article.getName();
	                String typeIds = article.getTypeIds();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
					if(StringUtils.isNotEmpty(typeIds)) {
						typeIdsp = cb.like(root.<String> get("typeIds"), "%" + typeIds + "%");
	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
	                 if(Optional.ofNullable(typeIdsp).isPresent()) query.where(typeIdsp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable,Article.class);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,Article.class);
		}else{
			return new Result<Page<Article>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 文章添加
	 * @param article
	 * @return Result<String>
	 */
	protected Result<String> getArticleAdd(Article article) {
		try {
			baserepository.save(article);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	/**
	 * 文章更新
	 * @param article
	 * @return Result<String>
	 */
	protected Result<String> getArticleUpdate(Article article) {
		try {
			baserepository.update(article);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章删除
	 * @param article
	 * @return Result<String>
	 */
	protected Result<String> getArticleDel(Article article) {
		try {
			baserepository.delete(article);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	

}