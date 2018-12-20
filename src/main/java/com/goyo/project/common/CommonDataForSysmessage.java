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

import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Sysmessage;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForSysmessage {

	// *********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<Sysmessage, String> baserepository;

	public CommonDataForSysmessage() {
		super();
	}

	/**
	 * 获取Sysmessage单个对象
	 * 
	 * @param Sysmessage
	 * @return Result<Sysmessage>
	 */
	protected Result<Sysmessage> getSysmessageSingle(Sysmessage Sysmessage) {
		return ResultGenerator.genSuccessResult(baserepository.findById(Sysmessage, Sysmessage.getId()));
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Sysmessage
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Sysmessage>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Sysmessage>> getSysmessagePage(Sysmessage sysmessage, String pageNum, String pageSize) {
		Page<Sysmessage> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Sysmessage>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Sysmessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					String subjection = sysmessage.getSubjection();
					try {
						subjection = URLDecoder.decode(subjection, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
						throw new RuntimeException("解码异常！");
					}
					// String organization = Sysmessage.getOrganization();
					if (null != sysmessage && !StringUtils.isEmpty(subjection)) {
						subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
					}
					// if(null != Sysmessage && !StringUtils.isEmpty(organization))
					// {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (null != subjectionp)
						query.where(subjectionp);
					// if(null != organizationp) query.where(organizationp);
					return null;
				}
			}, pageable, Sysmessage.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)) {
			page = baserepository.findAll(pageable, Sysmessage.class);
		} else {
			return new Result<Page<Sysmessage>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Sysmessage
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Sysmessage>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd
	 *                    HH:mm") page.getTotalElements();//总行数
	 *                    page.getTotalPages();//总页数 page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Sysmessage>> getSysmessagePage(Sysmessage sysmessage, String pageNum, String pageSize, Date startTime,
			Date endTime) {
		Page<Sysmessage> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Sysmessage>() {

				private static final long serialVersionUID = 3312137508342580834L;

				@Override
				public Predicate toPredicate(Root<Sysmessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					Predicate time = null;
					String subjection = sysmessage.getSubjection();
					try {
						subjection = URLDecoder.decode(subjection, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
						throw new RuntimeException("解码异常！");
					}
					// String organization = Sysmessage.getOrganization();
					if (StringUtils.isNotEmpty(subjection)) {
						subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
					}
					// if(StringUtils.isNotEmpty(organization)) {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("relTime"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("relTime"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("relTime"), endTime);
					}
					if (Optional.ofNullable(subjectionp).isPresent())
						query.where(subjectionp);
					// if(Optional.ofNullable(organizationp).isPresent())
					// query.where(organizationp);
					if (Optional.ofNullable(time).isPresent())
						query.where(time);
					return null;
				}
			}, pageable, Sysmessage.class);
		} else if ((StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize))
				&& ObjectUtils.anyNotNull(startTime, endTime)) {
			page = baserepository.findAll(new Specification<Sysmessage>() {

				private static final long serialVersionUID = -1661361568527407973L;

				@Override
				public Predicate toPredicate(Root<Sysmessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					Predicate time = null;
					String subjection = sysmessage.getSubjection();
					// String organization = Sysmessage.getOrganization();
					try {
						subjection = URLDecoder.decode(subjection, "UTF-8");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
						throw new RuntimeException("解码异常！");
					}
					if (StringUtils.isNotEmpty(subjection)) {
						subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
					}
					// if(StringUtils.isNotEmpty(organization)) {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("relTime"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("relTime"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("relTime"), endTime);
					}
					if (Optional.ofNullable(subjectionp).isPresent())
						query.where(subjectionp);
					// if(Optional.ofNullable(organizationp).isPresent())
					// query.where(organizationp);
					if (Optional.ofNullable(time).isPresent())
						query.where(time);
					return null;
				}
			}, pageable, Sysmessage.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)
				&& org.springframework.util.ObjectUtils.isEmpty(startTime)
				&& org.springframework.util.ObjectUtils.isEmpty(endTime)) {
			page = baserepository.findAll(pageable, Sysmessage.class);
		} else {
			return new Result<Page<Sysmessage>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 添加
	 * 
	 * @param Sysmessage
	 * @return Result<String>
	 */
	protected Result<String> getSysmessageAdd(Sysmessage sysmessage) {

		try {
			
			// baserepository.save(Sysmessage);
			baserepository.save(sysmessage);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章修改
	 * 
	 * @param Sysmessage
	 * @return Result<String>
	 */
	protected Result<String> getSysmessageUpdate(Sysmessage sysmessage) {
		try {
			baserepository.update(sysmessage);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章删除
	 * 
	 * @param Sysmessage
	 * @return Result<String>
	 */
	protected Result<String> getSysmessageDel(Sysmessage sysmessage) {
		try {
			baserepository.delete(sysmessage);
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