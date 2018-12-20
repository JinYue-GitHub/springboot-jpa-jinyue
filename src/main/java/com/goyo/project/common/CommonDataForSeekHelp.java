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
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Article;
import com.goyo.project.model.SeekHelp;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForSeekHelp {

	//*********************************************信访举报-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<SeekHelp,String> baserepository;
	
	/*@Resource
	private SeekHelpDao SeekHelpDao;*/
	

	public CommonDataForSeekHelp() {
		super();
	}

	/**
	 * 获取SeekHelp单个对象
	 * @param SeekHelp
	 * @return Result<SeekHelp>
	 */
	protected Result<SeekHelp> getSeekHelpSingle(SeekHelp seekHelp) {
		return ResultGenerator.genSuccessResult(baserepository.findById(seekHelp, seekHelp.getId()));
	}

	/**
	 * 分页对象
	 * @param SeekHelp
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<SeekHelp>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<SeekHelp>> getSeekHelpPage(SeekHelp seekHelp, String pageNum, String pageSize) {
		Page<SeekHelp> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(seekHelp.getPageNum())&&StringUtils.isNoneBlank(seekHelp.getPageSize())){
			int pageNumInt = Integer.parseInt(seekHelp.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(seekHelp.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<SeekHelp>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<SeekHelp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                String name = seekHelp.getName();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = SeekHelp.getOrganization();
					if(null != seekHelp && !StringUtils.isEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(null != SeekHelp && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
	                 if(null != namep) query.where(namep);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, SeekHelp.class);
		}else if(StringUtils.isBlank(seekHelp.getPageNum())&&StringUtils.isBlank(seekHelp.getPageSize())){
			page = baserepository.findAll(pageable,SeekHelp.class);
		}else{
			return new Result<Page<SeekHelp>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 分页对象
	 * @param SeekHelp
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<SeekHelp>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<SeekHelp>> getSeekHelpPage(SeekHelp seekHelp, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<SeekHelp> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(seekHelp.getPageNum())&&StringUtils.isNoneBlank(seekHelp.getPageSize())){
			int pageNumInt = Integer.parseInt(seekHelp.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(seekHelp.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<SeekHelp>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<SeekHelp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = seekHelp.getName();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = SeekHelp.getOrganization();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("pubTime"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("pubTime"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("pubTime"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         }, pageable, SeekHelp.class);
		}else if((StringUtils.isBlank(seekHelp.getPageNum())||StringUtils.isBlank(seekHelp.getPageSize())) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<SeekHelp>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<SeekHelp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = seekHelp.getName();
//	                String organization = SeekHelp.getOrganization();
	                try {
	                	if(StringUtils.isNoneBlank(name)){
	                		name = URLDecoder.decode(name, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("pubTime"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("pubTime"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("pubTime"), endTime);
	                }
	                 if(Optional.ofNullable(namep).isPresent()) query.where(namep);
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                 if(Optional.ofNullable(time).isPresent()) query.where(time);
	                 return null;
	             }
	         },pageable,SeekHelp.class);
		}else if(StringUtils.isBlank(seekHelp.getPageNum()) && StringUtils.isBlank(seekHelp.getPageSize()) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,SeekHelp.class);
		}else{
			return new Result<Page<SeekHelp>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 添加
	 * @param SeekHelp
	 * @return Result<String>
	 */
	protected Result<String> getSeekHelpAdd(SeekHelp seekHelp) {
		
		try {
			baserepository.save(seekHelp);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章修改
	 * 
	 * @param article
	 * @return Result<String>
	 */
	protected Result<String> getSeekHelpUpdate(SeekHelp seekHelp) {
		try {
			baserepository.update(seekHelp);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	
	/**
	 * 删除
	 * @param SeekHelp
	 * @return Result<String>
	 */
	protected Result<String> getSeekHelpDel(SeekHelp seekHelp) {
		try {
			baserepository.delete(seekHelp);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	
	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param request
	 * @return
	 */
	public Object fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
			String filePath) {

		return FileUploadUtils.fileUpload(file, request, filePath);
	}

	/***
	 * 文件下载
	 */
	public Object download(String filename, String filePath) throws Exception {

		return FileUploadUtils.listExport(filename, filePath);
	}

}