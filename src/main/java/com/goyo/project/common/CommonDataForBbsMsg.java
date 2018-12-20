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
import com.goyo.project.model.BbsMsg;
import com.goyo.project.model.UserInfo;
import com.goyo.project.model.BbsMsg;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForBbsMsg {

	//*********************************************信访举报-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<BbsMsg,String> baserepository;
	
	/*@Resource
	private BbsMsgDao BbsMsgDao;*/
	

	public CommonDataForBbsMsg() {
		super();
	}

	/**
	 * 获取BbsMsg单个对象
	 * @param BbsMsg
	 * @return Result<BbsMsg>
	 */
	protected Result<BbsMsg> getBbsMsgSingle(BbsMsg bbsMsg) {
		return ResultGenerator.genSuccessResult(baserepository.findById(bbsMsg, bbsMsg.getId()));
	}

	/**
	 * 分页对象
	 * @param BbsMsg
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<BbsMsg>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<BbsMsg>> getBbsMsgPage(BbsMsg bbsMsg, String pageNum, String pageSize) {
		Page<BbsMsg> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(bbsMsg.getPageNum())&&StringUtils.isNoneBlank(bbsMsg.getPageSize())){
			int pageNumInt = Integer.parseInt(bbsMsg.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(bbsMsg.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<BbsMsg>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<BbsMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                String bbsid = bbsMsg.getBbsid();
	                try {
	                	if(StringUtils.isNoneBlank(bbsid)){
	                		bbsid = URLDecoder.decode(bbsid, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsMsg.getOrganization();
					if(null != bbsMsg && !StringUtils.isEmpty(bbsid)) {
						namep = cb.like(root.<String> get("bbsid"), "%" + bbsid + "%");
	                }
//					if(null != BbsMsg && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
	                 if(null != namep) query.where(namep);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, BbsMsg.class);
		}else if(StringUtils.isBlank(bbsMsg.getPageNum())&&StringUtils.isBlank(bbsMsg.getPageSize())){
			page = baserepository.findAll(pageable,BbsMsg.class);
		}else{
			return new Result<Page<BbsMsg>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 分页对象
	 * @param BbsMsg
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<BbsMsg>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<BbsMsg>> getBbsMsgPage(BbsMsg bbsMsg, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<BbsMsg> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(bbsMsg.getPageNum())&&StringUtils.isNoneBlank(bbsMsg.getPageSize())){
			int pageNumInt = Integer.parseInt(bbsMsg.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(bbsMsg.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<BbsMsg>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<BbsMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate bbsidp = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String bbsid = bbsMsg.getBbsid();
	                try {
	                	if(StringUtils.isNoneBlank(bbsid)){
	                		bbsid = URLDecoder.decode(bbsid, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsMsg.getOrganization();
					if(StringUtils.isNotEmpty(bbsid)) {
						bbsidp = cb.like(root.<String> get("bbsid"), "%" + bbsid + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("pubtime"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("pubtime"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("pubtime"), endTime);
	                }
	                 if(Optional.ofNullable(bbsidp).isPresent()&&Optional.ofNullable(time).isPresent()){
	                	 organizationp = cb.and(bbsidp, time);
							query.where(organizationp);
	                 }else {
	                	 if(Optional.ofNullable(bbsidp).isPresent()) query.where(bbsidp);
//		                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
		                 if(Optional.ofNullable(time).isPresent()) query.where(time);
					}
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                
	                 return null;
	             }
	         }, pageable, BbsMsg.class);
		}else if((StringUtils.isBlank(bbsMsg.getPageNum())||StringUtils.isBlank(bbsMsg.getPageSize())) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<BbsMsg>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<BbsMsg> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate bbsidp = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String bbsid = bbsMsg.getBbsid();
//	                String organization = BbsMsg.getOrganization();
	                try {
	                	if(StringUtils.isNoneBlank(bbsid)){
	                		bbsid = URLDecoder.decode(bbsid, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
					if(StringUtils.isNotEmpty(bbsid)) {
						bbsidp = cb.like(root.<String> get("bbsid"), "%" + bbsid + "%");
	                }
//					if(StringUtils.isNotEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					if(Optional.ofNullable(startTime).isPresent() && 
						Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.between(root.<Date> get("pubtime"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("pubtime"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("pubtime"), endTime);
	                }
					if(Optional.ofNullable(bbsidp).isPresent()&&Optional.ofNullable(time).isPresent()){
	                	 organizationp = cb.and(bbsidp, time);
							query.where(organizationp);
	                 }else {
	                	 if(Optional.ofNullable(bbsidp).isPresent()) query.where(bbsidp);
//		                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
//		                 if(Optional.ofNullable(time).isPresent()) query.where(time);
					}
	                 return null;
	             }
	         },Pageable.unpaged(),BbsMsg.class);
		}else{
			return new Result<Page<BbsMsg>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 添加
	 * @param BbsMsg
	 * @return Result<String>
	 */
	protected Result<String> getBbsMsgAdd(BbsMsg bbsMsg) {
		
		try {
			UserInfo userInfo=CommonUtils.getCurrentUserInfo();
			bbsMsg.setUserno(userInfo.getUid().toString());
			bbsMsg.setUserName(userInfo.getName());
			baserepository.save(bbsMsg);
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
	protected Result<String> getBbsMsgUpdate(BbsMsg bbsMsg) {
		try {
			baserepository.update(bbsMsg);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	
	/**
	 * 删除
	 * @param BbsMsg
	 * @return Result<String>
	 */
	protected Result<String> getBbsMsgDel(BbsMsg bbsMsg) {
		try {
			baserepository.delete(bbsMsg);
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