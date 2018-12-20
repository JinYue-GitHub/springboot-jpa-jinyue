package com.goyo.project.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement.Else;
import com.google.common.collect.Lists;
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Personnel;
import com.goyo.project.model.Personnel;
import com.goyo.project.model.UserInfo;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForPersonnel {

	// *********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<Personnel, String> baserepository;

	public CommonDataForPersonnel() {
		super();
	}

	/**
	 * 获取Personnel单个对象
	 * 
	 * @param Personnel
	 * @return Result<Personnel>
	 */
	protected Result<Personnel> getPersonnelSingle(Personnel personnel) {
		return ResultGenerator.genSuccessResult(baserepository.findById(personnel, personnel.getId()));
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Personnel
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Personnel>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Personnel>> getPersonnelPage(Personnel personnel, String pageNum, String pageSize) {
		Page<Personnel> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Personnel>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Personnel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Predicate organizationp = null;
					Predicate namep = null;
					Predicate hmcTypep = null;
					Predicate companyp = null;
					String name = personnel.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String hmcType = personnel.getHmcType();
					if (StringUtils.isNotEmpty(hmcType)) {
						try {
							hmcType = URLDecoder.decode(hmcType, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(hmcType)) {
							hmcTypep = cb.like(root.<String>get("hmcType"), "%" + hmcType + "%");
						}
					}
					String company = personnel.getCompany();
					if (StringUtils.isNotEmpty(company)) {
						try {
							company = URLDecoder.decode(company, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(company)) {
							companyp = cb.like(root.<String>get("company"), "%" + company + "%");
						}
					}

					// if(null != Personnel &&
					// !StringUtils.isEmpty(organization))
					// {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (Optional.ofNullable(companyp).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(hmcTypep, companyp);
							query.where(organizationp);

						} else {

							if (Optional.ofNullable(companyp).isPresent())
								query.where(companyp);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent())
							query.where(namep);

						if (Optional.ofNullable(hmcTypep).isPresent())
							query.where(hmcTypep);

						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep);
							query.where(organizationp);
						}

					}

					// if(null != organizationp) query.where(organizationp);
					return null;
				}
			}, pageable, Personnel.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)) {
			page = baserepository.findAll(pageable, Personnel.class);
		} else {
			return new Result<Page<Personnel>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Personnel
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Personnel>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd
	 *                    HH:mm") page.getTotalElements();//总行数
	 *                    page.getTotalPages();//总页数 page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Personnel>> getPersonnelPage(Personnel personnel, String pageNum, String pageSize,
			Date startTime, Date endTime) {
		Page<Personnel> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Personnel>() {

				private static final long serialVersionUID = 3312137508342580834L;

				@Override
				public Predicate toPredicate(Root<Personnel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate organizationp = null;
					Predicate namep = null;
					Predicate hmcTypep = null;
					Predicate companyp = null;
					String name = personnel.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String hmcType = personnel.getHmcType();
					if (StringUtils.isNotEmpty(hmcType)) {
						try {
							hmcType = URLDecoder.decode(hmcType, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(hmcType)) {
							hmcTypep = cb.like(root.<String>get("hmcType"), "%" + hmcType + "%");
						}
					}
					String company = personnel.getCompany();
					if (StringUtils.isNotEmpty(company)) {
						try {
							company = URLDecoder.decode(company, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(company)) {
							companyp = cb.like(root.<String>get("company"), "%" + company + "%");
						}
					}

					if (Optional.ofNullable(companyp).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(hmcTypep, companyp);
							query.where(organizationp);

						} else {

							if (Optional.ofNullable(companyp).isPresent())
								query.where(companyp);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							query.where(namep);
						} else if (Optional.ofNullable(hmcTypep).isPresent()){
								query.where(hmcTypep);
						}

					}
					return null;
				}
			}, pageable, Personnel.class);
		} else if ((StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize))
				&& ObjectUtils.anyNotNull(startTime, endTime)) {
			page = baserepository.findAll(new Specification<Personnel>() {

				private static final long serialVersionUID = -1661361568527407973L;

				@Override
				public Predicate toPredicate(Root<Personnel> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate organizationp = null;
					Predicate namep = null;
					Predicate hmcTypep = null;
					Predicate companyp = null;
					String name = personnel.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String hmcType = personnel.getHmcType();
					if (StringUtils.isNotEmpty(hmcType)) {
						try {
							hmcType = URLDecoder.decode(hmcType, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(hmcType)) {
							hmcTypep = cb.like(root.<String>get("hmcType"), "%" + hmcType + "%");
						}
					}
					String company = personnel.getCompany();
					if (StringUtils.isNotEmpty(company)) {
						try {
							company = URLDecoder.decode(company, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = Personnel.getOrganization();
						if (StringUtils.isNotEmpty(company)) {
							companyp = cb.like(root.<String>get("company"), "%" + company + "%");
						}
					}

					if (Optional.ofNullable(companyp).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, companyp);
							query.where(organizationp);

						} else if (Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(hmcTypep, companyp);
							query.where(organizationp);

						} else {

							if (Optional.ofNullable(companyp).isPresent())
								query.where(companyp);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(hmcTypep).isPresent()) {
							organizationp = cb.and(namep, hmcTypep);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							query.where(namep);
						} else if (Optional.ofNullable(hmcTypep).isPresent()){
								query.where(hmcTypep);
						}

					}
					return null;
				}
			}, pageable, Personnel.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)
				&& org.springframework.util.ObjectUtils.isEmpty(startTime)
				&& org.springframework.util.ObjectUtils.isEmpty(endTime)) {
			page = baserepository.findAll(pageable, Personnel.class);
		} else {
			return new Result<Page<Personnel>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 文章添加
	 * 
	 * @param Personnel
	 * @return Result<String>
	 */
	protected Result<String> getPersonnelAdd(Personnel personnel) {

		try {

			// baserepository.save(Personnel);
			baserepository.save(personnel);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章修改
	 * 
	 * @param Personnel
	 * @return Result<String>
	 */
	protected Result<String> getPersonnelUpdate(Personnel personnel) {
		try {
			baserepository.update(personnel);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章删除
	 * 
	 * @param Personnel
	 * @return Result<String>
	 */
	protected Result<String> getPersonnelDel(Personnel personnel) {
		try {
			baserepository.delete(personnel);
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