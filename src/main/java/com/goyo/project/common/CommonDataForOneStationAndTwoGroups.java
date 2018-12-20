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
import com.goyo.project.model.OneStationAndTwoGroups;
import com.goyo.project.utils.BeanUtils;

public class CommonDataForOneStationAndTwoGroups {

	//*********************************************一站两群-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<OneStationAndTwoGroups,String> baserepository;

	public CommonDataForOneStationAndTwoGroups() {
		super();
	}

	/**
	 * 获取OneStationAndTwoGroups单个对象
	 * @param oneStationAndTwoGroups
	 * @return Result<OneStationAndTwoGroups>
	 */
	protected Result<OneStationAndTwoGroups> getOneStationAndTwoGroupsSingle(OneStationAndTwoGroups oneStationAndTwoGroups) {
		return ResultGenerator.genSuccessResult(baserepository.findById(oneStationAndTwoGroups, oneStationAndTwoGroups.getId()));
	}

	/**
	 * 获取一站两群分页对象
	 * @param oneStationAndTwoGroups
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<OneStationAndTwoGroups>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<OneStationAndTwoGroups>> getOneStationAndTwoGroupsPage(OneStationAndTwoGroups oneStationAndTwoGroups, String pageNum, String pageSize) {
		Page<OneStationAndTwoGroups> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<OneStationAndTwoGroups>() {
				
				private static final long serialVersionUID = 1L;

				@Override
	            public Predicate toPredicate(Root<OneStationAndTwoGroups> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                String name = oneStationAndTwoGroups.getName();
	                try {
	                	name = URLDecoder.decode(name, "UTF-8");
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = oneStationAndTwoGroups.getOrganization();
					if(null != oneStationAndTwoGroups && !StringUtils.isEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
	                }
//					if(null != oneStationAndTwoGroups && !StringUtils.isEmpty(organization)) {
//						organizationp = cb.like(root.<String> get("organization"), "%" + organization + "%");
//	                }
	                 if(null != namep) query.where(namep);
//	                 if(null != organizationp) query.where(organizationp);
	                 return null;
	             }
	         }, pageable, OneStationAndTwoGroups.class);
		}else if(StringUtils.isBlank(pageNum)&&StringUtils.isBlank(pageSize)){
			page = baserepository.findAll(pageable,OneStationAndTwoGroups.class);
		}else{
			return new Result<Page<OneStationAndTwoGroups>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}
	
	/**
	 * 获取一站两群分页对象
	 * @param oneStationAndTwoGroups
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<OneStationAndTwoGroups>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
	 * 	page.getTotalElements();//总行数
	 * 	page.getTotalPages();//总页数
	 * 	page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<OneStationAndTwoGroups>> getOneStationAndTwoGroupsPage(OneStationAndTwoGroups oneStationAndTwoGroups, String pageNum, String pageSize,Date startTime,Date endTime) {
		Page<OneStationAndTwoGroups> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt-=1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = baserepository.findAll(new Specification<OneStationAndTwoGroups>() {
				
				private static final long serialVersionUID = 3312137508342580834L;

				@Override
	            public Predicate toPredicate(Root<OneStationAndTwoGroups> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = oneStationAndTwoGroups.getName();
	                try {
	                	name = URLDecoder.decode(name, "UTF-8");
	        		} catch (UnsupportedEncodingException e1) {
	        			e1.printStackTrace();
	        			throw new RuntimeException("解码异常！");
	        		}
//	                String organization = oneStationAndTwoGroups.getOrganization();
					if(StringUtils.isNotEmpty(name)) {
						namep = cb.like(root.<String> get("name"), "%" + name + "%");
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
	                 
//	                 if(Optional.ofNullable(organizationp).isPresent()) query.where(organizationp);
	                
	                 if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(time).isPresent()) {
							organizationp = cb.and(namep,  time);
							query.where(organizationp);
						}else if(Optional.ofNullable(namep).isPresent()){
							query.where(namep);
							
						}else  if(Optional.ofNullable(time).isPresent()){
							query.where(time);
						}
	                 
	                 return null;
	             }
	         }, pageable, OneStationAndTwoGroups.class);
		}else if((StringUtils.isBlank(pageNum)||StringUtils.isBlank(pageSize)) && ObjectUtils.anyNotNull(startTime,endTime)){
			page = baserepository.findAll(new Specification<OneStationAndTwoGroups>() {
				
				private static final long serialVersionUID = -1661361568527407973L;

				@Override
	            public Predicate toPredicate(Root<OneStationAndTwoGroups> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                
	                Predicate namep = null;
	                Predicate organizationp = null;
	                Predicate time = null;
	                String name = oneStationAndTwoGroups.getName();
//	                String organization = oneStationAndTwoGroups.getOrganization();
	                try {
	                	name = URLDecoder.decode(name, "UTF-8");
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
	                	time = cb.between(root.<Date> get("createDate"), startTime, endTime);
	                }else if(Optional.ofNullable(startTime).isPresent()){
	                	time = cb.greaterThan(root.<Date> get("createDate"),startTime);
	                }else if(Optional.ofNullable(endTime).isPresent()) {
	                	time = cb.lessThan(root.<Date> get("createDate"), endTime);
	                }
					if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(time).isPresent()) {
						organizationp = cb.and(namep,  time);
						query.where(organizationp);
					}else if(Optional.ofNullable(namep).isPresent()){
						query.where(namep);
						
					}else  if(Optional.ofNullable(time).isPresent()){
						query.where(time);
					}
	                 return null;
	             }
	         },pageable,OneStationAndTwoGroups.class);
		}else if(StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize) && org.springframework.util.ObjectUtils.isEmpty(startTime) && org.springframework.util.ObjectUtils.isEmpty(endTime)){
			page = baserepository.findAll(pageable,OneStationAndTwoGroups.class);
		}else{
			return new Result<Page<OneStationAndTwoGroups>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}


	/**
	 * 一站两群添加
	 * @param oneStationAndTwoGroups
	 * @return Result<String>
	 */
	protected Result<String> getOneStationAndTwoGroupsAdd(OneStationAndTwoGroups oneStationAndTwoGroups) {
		
		try {
			baserepository.save(oneStationAndTwoGroups);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 一站两群删除
	 * @param oneStationAndTwoGroups
	 * @return Result<String>
	 */
	protected Result<String> getOneStationAndTwoGroupsDel(OneStationAndTwoGroups oneStationAndTwoGroups) {
		try {
			baserepository.delete(oneStationAndTwoGroups);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	
	/**
	 * 一站两群修改
	 * @param oneStationAndTwoGroups
	 * @return Result<String>
	 */
	protected Result<String> getOneStationAndTwoGroupsUpdate(OneStationAndTwoGroups oneStationAndTwoGroups) {
		try {
			OneStationAndTwoGroups oneStationAndTwoGroupsDB = baserepository.findById(oneStationAndTwoGroups, oneStationAndTwoGroups.getId());
			BeanUtils.copyProperties(oneStationAndTwoGroups, oneStationAndTwoGroupsDB);
			baserepository.update(oneStationAndTwoGroupsDB);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}
	

}