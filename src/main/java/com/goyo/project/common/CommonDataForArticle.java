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
import com.goyo.project.model.Article;
import com.goyo.project.model.UserInfo;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.utils.FileUploadUtils;

public class CommonDataForArticle {

	// *********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private BaseRepository<Article, String> baserepository;

	public CommonDataForArticle() {
		super();
	}

	/**
	 * 获取Article单个对象
	 * 
	 * @param article
	 * @return Result<Article>
	 */
	protected Result<Article> getArticleSingle(Article article) {
		return ResultGenerator.genSuccessResult(baserepository.findById(article, article.getId()));
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Article>>
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Article>> getArticlePage(Article article, String pageNum, String pageSize) {
		Page<Article> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Article>() {

				private static final long serialVersionUID = 1L;

				@Override
				public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					Predicate namep = null;
					String name = article.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = article.getOrganization();
						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String subjection = article.getSubjection();
					if (StringUtils.isNotEmpty(subjection)) {
						try {
							subjection = URLDecoder.decode(subjection, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}
						// String organization = article.getOrganization();
						if (StringUtils.isNotEmpty(subjection)) {
							subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
						}
					}

					// if(null != article && !StringUtils.isEmpty(organization))
					// {
					// organizationp = cb.like(root.<String>
					// get("organization"), "%" + organization + "%");
					// }
					if (Optional.ofNullable(namep).isPresent())
						query.where(namep);
					if (null != subjectionp)
						query.where(subjectionp);
					
					if (Optional.ofNullable(namep).isPresent()&&Optional.ofNullable(subjectionp).isPresent()){
						organizationp = cb.and(namep, subjectionp);
						query.where(organizationp);
					}
					// if(null != organizationp) query.where(organizationp);
					return null;
				}
			}, pageable, Article.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)) {
			page = baserepository.findAll(pageable, Article.class);
		} else {
			return new Result<Page<Article>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 获取文章分页对象
	 * 
	 * @param article
	 * @param pageNum
	 * @param pageSize
	 * @return Result<Page<Article>>
	 * @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	 * @JsonFormat(locale = "zh",timezone = "GMT+8", pattern = "yyyy-MM-dd
	 *                    HH:mm") page.getTotalElements();//总行数
	 *                    page.getTotalPages();//总页数 page.getContent();//分页结果集
	 */
	@SuppressWarnings("deprecation")
	protected Result<Page<Article>> getArticlePage(Article article, String pageNum, String pageSize, Date startTime,
			Date endTime) {
		Page<Article> page = null;
		Pageable pageable = null;
		if (StringUtils.isNoneBlank(pageNum) && StringUtils.isNoneBlank(pageSize)) {
			int pageNumInt = Integer.parseInt(pageNum);
			pageNumInt -= 1;
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt, pageSizeInt);
			page = baserepository.findAll(new Specification<Article>() {

				private static final long serialVersionUID = 3312137508342580834L;

				@Override
				public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					Predicate time = null;
					Predicate namep = null;
					String name = article.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String subjection = article.getSubjection();
					if (StringUtils.isNotEmpty(subjection)) {
						try {
							subjection = URLDecoder.decode(subjection, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(subjection)) {
							subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
						}
					}
					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("updateDate"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("updateDate"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("updateDate"), endTime);
					}

					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(subjectionp).isPresent()) {
							organizationp = cb.and(namep, subjectionp, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(subjectionp).isPresent()) {
							organizationp = cb.and(subjectionp, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(namep).isPresent())
								query.where(namep);

							if (Optional.ofNullable(subjectionp).isPresent())
								query.where(subjectionp);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent())
							query.where(namep);

						if (Optional.ofNullable(subjectionp).isPresent())
							query.where(subjectionp);
						
						if (Optional.ofNullable(namep).isPresent()&&Optional.ofNullable(subjectionp).isPresent()){
							organizationp = cb.and(namep, subjectionp);
							query.where(organizationp);
						}

					}
					return null;
				}
			}, pageable, Article.class);
		} else if ((StringUtils.isBlank(pageNum) || StringUtils.isBlank(pageSize))
				&& ObjectUtils.anyNotNull(startTime, endTime)) {
			page = baserepository.findAll(new Specification<Article>() {

				private static final long serialVersionUID = -1661361568527407973L;

				@Override
				public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

					Predicate subjectionp = null;
					Predicate organizationp = null;
					Predicate time = null;
					Predicate namep = null;
					String name = article.getName();
					if (StringUtils.isNotEmpty(name)) {
						try {
							name = URLDecoder.decode(name, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(name)) {
							namep = cb.like(root.<String>get("name"), "%" + name + "%");
						}
					}

					String subjection = article.getSubjection();
					if (StringUtils.isNotEmpty(subjection)) {
						try {
							subjection = URLDecoder.decode(subjection, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							e1.printStackTrace();
							throw new RuntimeException("解码异常！");
						}

						if (StringUtils.isNotEmpty(subjection)) {
							subjectionp = cb.like(root.<String>get("subjection"), "%" + subjection + "%");
						}
					}

					if (Optional.ofNullable(startTime).isPresent() && Optional.ofNullable(endTime).isPresent()) {
						time = cb.between(root.<Date>get("updateDate"), startTime, endTime);
					} else if (Optional.ofNullable(startTime).isPresent()) {
						time = cb.greaterThan(root.<Date>get("updateDate"), startTime);
					} else if (Optional.ofNullable(endTime).isPresent()) {
						time = cb.lessThan(root.<Date>get("updateDate"), endTime);
					}

					if (Optional.ofNullable(time).isPresent()) {
						if (Optional.ofNullable(namep).isPresent() && Optional.ofNullable(subjectionp).isPresent()) {
							organizationp = cb.and(namep, subjectionp, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(namep).isPresent()) {
							organizationp = cb.and(namep, time);
							query.where(organizationp);
						} else if (Optional.ofNullable(subjectionp).isPresent()) {
							organizationp = cb.and(subjectionp, time);
							query.where(organizationp);
						} else {
							if (Optional.ofNullable(namep).isPresent())
								query.where(namep);

							if (Optional.ofNullable(subjectionp).isPresent())
								query.where(subjectionp);

							if (Optional.ofNullable(time).isPresent())
								query.where(time);

						}

					} else {
						if (Optional.ofNullable(namep).isPresent())
							query.where(namep);

						if (Optional.ofNullable(subjectionp).isPresent())
							query.where(subjectionp);
						
						if (Optional.ofNullable(namep).isPresent()&&Optional.ofNullable(subjectionp).isPresent()){
							organizationp = cb.and(namep, subjectionp);
							query.where(organizationp);
						}
							

						
					}
					return null;
				}
			}, pageable, Article.class);
		} else if (StringUtils.isBlank(pageNum) && StringUtils.isBlank(pageSize)
				&& org.springframework.util.ObjectUtils.isEmpty(startTime)
				&& org.springframework.util.ObjectUtils.isEmpty(endTime)) {
			page = baserepository.findAll(pageable, Article.class);
		} else {
			return new Result<Page<Article>>().setCode(ResultCode.FAIL).setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
	}

	/**
	 * 文章添加
	 * 
	 * @param article
	 * @return Result<String>
	 */
	protected Result<String> getArticleAdd(Article article) {

		try {
			List<UserInfo> userList = Lists.newArrayList();
			userList.add(CommonUtils.getCurrentUserInfo());
			article.setUserInfoList(userList);
			// baserepository.save(article);
			baserepository.save(article);
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
	 * 
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