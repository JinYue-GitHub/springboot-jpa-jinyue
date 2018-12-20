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
import com.goyo.project.model.BbsInfo;
import com.goyo.project.model.UserInfo;
import com.goyo.project.model.BbsInfo;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForBbsInfo {

	//*********************************************信访举报-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<BbsInfo,String> baserepository;
	
	/*@Resource
	private BbsInfoDao BbsInfoDao;*/
	

	public CommonDataForBbsInfo() {
		super();
	}

	/**
	 * 获取BbsInfo单个对象
	 * @param BbsInfo
	 * @return Result<BbsInfo>
	 */
	protected Result<BbsInfo> getBbsInfoSingle(BbsInfo bbsInfo) {
		return ResultGenerator.genSuccessResult(baserepository.findById(bbsInfo, bbsInfo.getId()));
	}

	/**
	 * 分页对象
	 * @param BbsInfo
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<BbsInfo>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<BbsInfo>> getBbsInfoPage(BbsInfo bbsInfo, String pageNum, String pageSize) {
		UserInfo userInfo=CommonUtils.getCurrentUserInfo();
		bbsInfo.setUserno(userInfo.getUid().toString());
		Page<BbsInfo> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(bbsInfo.getPageNum())&&StringUtils.isNoneBlank(bbsInfo.getPageSize())){
			int pageNumInt = Integer.parseInt(bbsInfo.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(bbsInfo.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<BbsInfo>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<BbsInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate receiveUsernop = null;
	                Predicate usernp = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String userno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(userno)){
	                		userno = URLDecoder.decode(userno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						usernp = cb.like(root.<String> get("userno"), "%" + userno + "%");
	                }
					
					String receiveUserno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(receiveUserno)){
	                		userno = URLDecoder.decode(receiveUserno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						receiveUsernop = cb.like(root.<String> get("receiveUserno"), "%" + receiveUserno + "%");
	                }
//					if(null != BbsInfo && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
					 if(Optional.ofNullable(usernp).isPresent()) query.where(usernp);
	                 if(Optional.ofNullable(receiveUsernop).isPresent()) query.where(receiveUsernop);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, BbsInfo.class);
		}else if(StringUtils.isBlank(bbsInfo.getPageNum())&&StringUtils.isBlank(bbsInfo.getPageSize())){
			page = baserepository.findAll(pageable,BbsInfo.class);
		}else{
			return new Result<Page<BbsInfo>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 分页对象
	 * @param BbsInfo
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<BbsInfo>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<BbsInfo>> getBbsInfoPage(BbsInfo bbsInfo, String pageNum, String pageSize,Date startTime,Date endTime) {
		UserInfo userInfo=CommonUtils.getCurrentUserInfo();
		bbsInfo.setUserno(userInfo.getPhoneNumber());
		Page<BbsInfo> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(bbsInfo.getPageNum())&&StringUtils.isNoneBlank(bbsInfo.getPageSize())){
			int pageNumInt = Integer.parseInt(bbsInfo.getPageNum());
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(bbsInfo.getPageSize());
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<BbsInfo>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<BbsInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate receiveUsernop = null;
	                Predicate usernop = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String userno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(userno)){
	                		userno = URLDecoder.decode(userno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						usernop = cb.like(root.<String> get("userno"), "%" + userno + "%");
	                }
					
					String receiveUserno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(receiveUserno)){
	                		userno = URLDecoder.decode(receiveUserno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						receiveUsernop = cb.like(root.<String> get("receiveUserno"), "%" + receiveUserno + "%");
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
					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(usernop).isPresent() && Optional.ofNullable(receiveUsernop).isPresent()) {
							organizationp=cb.or(usernop, receiveUsernop);
							organizationp=cb.and(organizationp,time);
							query.where(organizationp);
						} else if (Optional.ofNullable(usernop).isPresent()) {
							organizationp = cb.and(usernop, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(receiveUsernop).isPresent()) {
							organizationp = cb.and(receiveUsernop, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(usernop).isPresent())
								query.where(usernop);

							if (Optional.ofNullable(receiveUsernop).isPresent())
								query.where(receiveUsernop);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(usernop).isPresent())
							query.where(usernop);

						if (Optional.ofNullable(receiveUsernop).isPresent())
							query.where(receiveUsernop);
						
						if (Optional.ofNullable(usernop).isPresent()&&Optional.ofNullable(receiveUsernop).isPresent()){
							organizationp = cb.or(usernop, receiveUsernop);
							query.where(organizationp);
						}

					}
	                 return null;
	             }
	         }, pageable, BbsInfo.class);
		}else if((StringUtils.isBlank(bbsInfo.getPageNum())||StringUtils.isBlank(bbsInfo.getPageSize())) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<BbsInfo>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<BbsInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
					Predicate receiveUsernop = null;
	                Predicate usernop = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String userno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(userno)){
	                		userno = URLDecoder.decode(userno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						usernop = cb.like(root.<String> get("userno"), "%" + userno + "%");
	                }
					
					String receiveUserno = bbsInfo.getUserno();
	                try {
	                	if(StringUtils.isNoneBlank(receiveUserno)){
	                		userno = URLDecoder.decode(receiveUserno, "UTF-8");
	                	}
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = BbsInfo.getOrganization();
					if(StringUtils.isNotEmpty(userno)) {
						receiveUsernop = cb.like(root.<String> get("receiveUserno"), "%" + receiveUserno + "%");
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
					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(usernop).isPresent() && Optional.ofNullable(receiveUsernop).isPresent()) {
							organizationp=cb.or(usernop, receiveUsernop);
							organizationp=cb.and(organizationp,time);
							query.where(organizationp);
						} else if (Optional.ofNullable(usernop).isPresent()) {
							organizationp = cb.and(usernop, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(receiveUsernop).isPresent()) {
							organizationp = cb.and(receiveUsernop, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(usernop).isPresent())
								query.where(usernop);

							if (Optional.ofNullable(receiveUsernop).isPresent())
								query.where(receiveUsernop);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(usernop).isPresent())
							query.where(usernop);

						if (Optional.ofNullable(receiveUsernop).isPresent())
							query.where(receiveUsernop);
						
						if (Optional.ofNullable(usernop).isPresent()&&Optional.ofNullable(receiveUsernop).isPresent()){
							organizationp = cb.or(usernop, receiveUsernop);
							query.where(organizationp);
						}

					}
	                 return null;
	             }
	         },pageable,BbsInfo.class);
		}else if(StringUtils.isBlank(bbsInfo.getPageNum()) && StringUtils.isBlank(bbsInfo.getPageSize()) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,BbsInfo.class);
		}else{
			return new Result<Page<BbsInfo>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 添加
	 * @param BbsInfo
	 * @return Result<String>
	 */
	protected Result<String> getBbsInfoAdd(BbsInfo bbsInfo) {
		
		try {
			UserInfo userInfo=CommonUtils.getCurrentUserInfo();
			bbsInfo.setUserno(userInfo.getPhoneNumber());
			baserepository.save(bbsInfo);
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
	protected Result<String> getBbsInfoUpdate(BbsInfo bbsInfo) {
		try {
			baserepository.update(bbsInfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	
	/**
	 * 删除
	 * @param BbsInfo
	 * @return Result<String>
	 */
	protected Result<String> getBbsInfoDel(BbsInfo bbsInfo) {
		try {
			baserepository.delete(bbsInfo);
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