package com.goyo.project.xiaoxi.web;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.common.CommonDataForSysmessage;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
import com.goyo.project.model.Sysmessage;

/**
 * @author: gdd
 * @ClassName: XFAJDBSysmessageController 
 * @Description: 信访案件督办
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/xxtzsysmessage")
public class XXTZSysmessageController extends CommonDataForSysmessage {
	    /**
	     * 指定类别查询(显示固定条数)
	     * @return Result<Page<Sysmessage>>
	     */
		@PostMapping("/sysmessagePage")
	    public Result<Page<Sysmessage>> Sysmessage(Sysmessage sysmessage,String pageNum,String pageSize){
			return getSysmessagePage(sysmessage, pageNum, pageSize);
	    }
		 /**
	     * 指定类别查询(更多  带分页)
	     * @return Result<Page<Sysmessage>>
	     */
		
		@PostMapping("/sysmessagePageMore")
	    public Result<Page<Sysmessage>> SysmessageMore(Sysmessage sysmessage, String pageNum, String pageSize,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 根据情况
																				// 再扩张更多查询条件
			return getSysmessagePage(sysmessage, pageNum, pageSize, startTime, endTime);
		}
		
		
		 /**
	     * 详情(single)
	     * @return Result<Sysmessage>
	     */
		
		@PostMapping("/sysmessageSingle")
	    public Result<Sysmessage> SysmessageSingle(Sysmessage sysmessage){
			return getSysmessageSingle(sysmessage);
	    }
		
		 /**
	     * 更新;( 新增)
	     * @return Result<String>
	     */
		@PostMapping("/sysmessageAdd")
	    public Result<String> SysmessageAdd(Sysmessage sysmessage){
			return getSysmessageAdd(sysmessage);
	       
	    }
		
		 /**
	     * 更新;(上传/发布)
	     * @return Result<String>
	     */
		@PostMapping("/sysmessageUpdate")
	    public Result<String> SysmessageUpdate(Sysmessage sysmessage){
			return getSysmessageUpdate(sysmessage);
	       
	    }
	    /**
	     * 删除;
	     * @return Result<String>
	     */
		@PostMapping("/sysmessageDel")
	    public Result<String> SysmessageDel(Sysmessage sysmessage){
			return getSysmessageDel(sysmessage);
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
			String filePath="xfajdb";
			
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
