package com.goyo.project.xinfang.web;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.common.CommonDataForCaseinfo;
import com.goyo.project.core.Result;
import com.goyo.project.model.Caseinfo;

/**
 * @author: gdd
 * @ClassName: XFLYXXHZCaseinfoController 
 * @Description: 信访来源信息汇总
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/xflyxxhzCaseinfo")
public class XFLYXXHZCaseinfoController extends CommonDataForCaseinfo {
	    /**
	     * 指定类别查询(显示固定条数)
	     * @return Result<Page<Caseinfo>>
	     */
		@PostMapping("/caseinfoPage")
	    public Result<Page<Caseinfo>> Caseinfo(Caseinfo caseinfo,String pageNum,String pageSize){
			return getCaseinfoPage(caseinfo, pageNum, pageSize);
	    }
		 /**
	     * 指定类别查询(更多  带分页)
	     * @return Result<Page<Caseinfo>>
	     */
		@PostMapping("/caseinfoPageMore")
	    public Result<Page<Caseinfo>> CaseinfoMore(Caseinfo caseinfo, String pageNum, String pageSize,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 根据情况
																				// 再扩张更多查询条件
			return getCaseinfoPage(caseinfo, pageNum, pageSize, startTime, endTime);
		}
		 /**
	     * 详情(single)
	     * @return Result<Caseinfo>
	     */
		
		@PostMapping("/caseinfoSingle")
	    public Result<Caseinfo> CaseinfoSingle(Caseinfo caseinfo){
			return getCaseinfoSingle(caseinfo);
	    }
		
		 /**
	     * 更新;( 新增)
	     * @return Result<String>
	     */
		@PostMapping("/caseinfoAdd")
	    public Result<String> CaseinfoAdd(Caseinfo caseinfo){
			return getCaseinfoAdd(caseinfo);
	       
	    }
		
		 /**
	     * 更新;(文章 上传/发布)
	     * @return Result<String>
	     */
		@PostMapping("/caseinfoUpdate")
	    public Result<String> CaseinfoUpdate(Caseinfo caseinfo){
			return getCaseinfoUpdate(caseinfo);
	       
	    }
	    /**
	     * 删除;
	     * @return Result<String>
	     */
		@PostMapping("/caseinfoDel")
	    public Result<String> CaseinfoDel(Caseinfo caseinfo){
			return getCaseinfoDel(caseinfo);
	    }
		
		/***
		 * 文件上传
		 * @param file
		 * @param request
		 * @param filePath
		 * @return
		 */
		@PostMapping("/fileUpload")
		public Object fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request){
			//文件位置
			String filePath="xflyxxhz";
			
			return fileUpload(file, request, filePath);
	    }
		
		/***
		 * 文件下载
		 */
		@PostMapping("/download")
		public Object downloads(String filename,String filePath)throws Exception{
		
			
			return download(filename,filePath);
	    }
}
