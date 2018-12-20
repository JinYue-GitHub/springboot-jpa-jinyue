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

import com.google.common.collect.Lists;
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Caseinfo;
import com.goyo.project.model.Caseinfo;
import com.goyo.project.model.Caseinfo;
import com.goyo.project.model.SeekHelp;
import com.goyo.project.model.UserInfo;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForCaseinfo {

	// *********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<Caseinfo, String> baserepository;

	public CommonDataForCaseinfo() {
		super();
	}

	/**
	 * 获取Caseinfo单个对象
	 * 
	 * @param Caseinfo
	 * @return Result<Caseinfo>
	 */
	protected Result<Caseinfo> getCaseinfoSingle(Caseinfo caseinfo) {
		return ResultGenerator.genSuccessResult(baserepository.findById(caseinfo, caseinfo.getId()));
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Caseinfo
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Caseinfo>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Caseinfo>> getCaseinfoPage(Caseinfo caseinfo, String pageNum, String pageSize) {
		Page<Caseinfo> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(caseinfo.getPageNum()) && StringUtils.isNoneBlank(caseinfo.getPageSize())) {
			int pageNumInt = Integer.parseInt(caseinfo.getPageNum());
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(caseinfo.getPageSize());
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Caseinfo>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Caseinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate namep = null;
					Predicate organizationp = null;
					String state = caseinfo.getState();
					try {
						if (StringUtils.isNoneBlank(state)) {
							state = URLDecoder.decode(state, "UTF-8");
						}
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
						throw new RuntimeException("解码异常！");
					}
					// String organization = SeekHelp.getOrganization();
					if (null != caseinfo && !StringUtils.isEmpty(state)) {
						namep = cb.like(root.<String>get("state"), "%" + state + "%");
					}
					// if(null != SeekHelp &&
					// !StringUtils.isEmpty(organization)) {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (null != namep)
						query.where(namep);
					// if(null != organizationp) query.where(organizationp);
					return null;
				}
			}, pageable, Caseinfo.class);
		} else if (StringUtils.isBlank(caseinfo.getPageNum()) && StringUtils.isBlank(caseinfo.getPageSize())) {
			page = baserepository.findAll(pageable, Caseinfo.class);
		} else {
			return new Result<Page<Caseinfo>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param Caseinfo
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Caseinfo>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd
	 *                    HH:mm") page.getTotalElements();//总行数
	 *                    page.getTotalPages();//总页数 page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Caseinfo>> getCaseinfoPage(Caseinfo caseinfo, String pageNum, String pageSize, Date startTime,
			Date endTime) {
		Page<Caseinfo> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(caseinfo.getPageNum()) && StringUtils.isNoneBlank(caseinfo.getPageSize())) {
			int pageNumInt = Integer.parseInt(caseinfo.getPageNum());
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(caseinfo.getPageSize());
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Caseinfo>() {

				private static final long serialVersionUID = 3312137508342580834L;

				@Override
				public Predicate toPredicate(Root<Caseinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate statep = null;
					Predicate organizationp = null;
					Predicate time = null;
					Predicate namep = null;
					String names = caseinfo.getNames();
					if (StringUtils.isNotEmpty(names)) {
						try {
							names = URLDecoder.decode(names, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(names)) {
							namep = cb.like(root.<String>get("names"), "%" + names + "%");
						}
					}

					String state = caseinfo.getState();
					if (StringUtils.isNotEmpty(state)) {
						try {
							state = URLDecoder.decode(state, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(state)) {
							statep = cb.like(root.<String>get("state"), "%" + state + "%");
						}
					}
					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("recDate"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("recDate"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("recDate"), endTime);
					}

					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(statep).isPresent()) {
							organizationp = cb.and(namep, statep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(statep).isPresent()) {
							organizationp = cb.and(statep, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(namep).isPresent())
								query.where(namep);

							if (Optional.ofNullable(statep).isPresent())
								query.where(statep);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent())
							query.where(namep);

						if (Optional.ofNullable(statep).isPresent())
							query.where(statep);
						
						if (Optional.ofNullable(namep).isPresent()&&Optional.ofNullable(statep).isPresent()){
							organizationp = cb.and(namep, statep);
							query.where(organizationp);
						}
					}

					return null;
				}
			}, pageable, Caseinfo.class);
		} else if ((StringUtils.isBlank(caseinfo.getPageNum()) || StringUtils.isBlank(caseinfo.getPageSize()))
				&& ObjectUtils.anyNotNull(startTime, endTime)) {
			page = baserepository.findAll(new Specification<Caseinfo>() {

				private static final long serialVersionUID = -1661361568527407973L;

				@Override
				public Predicate toPredicate(Root<Caseinfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate statep = null;
					Predicate organizationp = null;
					Predicate time = null;
					Predicate namep = null;
					String names = caseinfo.getNames();
					if (StringUtils.isNotEmpty(names)) {
						try {
							names = URLDecoder.decode(names, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(names)) {
							namep = cb.like(root.<String>get("names"), "%" + names + "%");
						}
					}

					String state = caseinfo.getState();
					if (StringUtils.isNotEmpty(state)) {
						try {
							state = URLDecoder.decode(state, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(state)) {
							statep = cb.like(root.<String>get("state"), "%" + state + "%");
						}
					}

					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("recDate"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("recDate"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("recDate"), endTime);
					}

					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(statep).isPresent()) {
							organizationp = cb.and(namep, statep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(statep).isPresent()) {
							organizationp = cb.and(statep, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(namep).isPresent())
								query.where(namep);

							if (Optional.ofNullable(statep).isPresent())
								query.where(statep);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent())
							query.where(namep);

						if (Optional.ofNullable(statep).isPresent())
							query.where(statep);
						
						if (Optional.ofNullable(namep).isPresent()&&Optional.ofNullable(statep).isPresent()){
							organizationp = cb.and(namep, statep);
							query.where(organizationp);
						}
					}

					return null;
				}
			}, pageable, Caseinfo.class);
		} else if (StringUtils.isBlank(caseinfo.getPageNum()) && StringUtils.isBlank(caseinfo.getPageSize())
				&& org.springframework.util.ObjectUtils.isEmpty(startTime)
				&& org.springframework.util.ObjectUtils.isEmpty(endTime)) {
			page = baserepository.findAll(pageable, Caseinfo.class);
		} else {
			return new Result<Page<Caseinfo>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 文章添加
	 * 
	 * @param Caseinfo
	 * @return Result<String>
	 */
	protected Result<String> getCaseinfoAdd(Caseinfo caseinfo) {

		try {

			// baserepository.save(Caseinfo);
			baserepository.save(caseinfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章修改
	 * 
	 * @param Caseinfo
	 * @return Result<String>
	 */
	protected Result<String> getCaseinfoUpdate(Caseinfo caseinfo) {
		try {
			baserepository.update(caseinfo);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}

	/**
	 * 文章删除
	 * 
	 * @param Caseinfo
	 * @return Result<String>
	 */
	protected Result<String> getCaseinfoDel(Caseinfo Caseinfo) {
		try {
			baserepository.delete(Caseinfo);
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