package com.goyo.project.huamingce;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.goyo.project.common.CommonDataForPersonnel;
import com.goyo.project.common.CommonDataForPersonnel;
import com.goyo.project.core.Result;
import com.goyo.project.model.Personnel;

/**
 * @author: gdd
 * @ClassName: HMCPersonnelController 
 * @Description: 花名册
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/hmcPersonnel")
public class HMCPersonnelController extends CommonDataForPersonnel {
	    /**
	     * 指定类别查询(显示固定条数)
	     * @return Result<Page<Personnel>>
	     */
		@PostMapping("/personnelPage")
	    public Result<Page<Personnel>> Personnel(Personnel personnel,String pageNum,String pageSize){
			return getPersonnelPage(personnel, pageNum, pageSize);
	    }
		 /**
	     * 指定类别查询(更多  带分页)
	     * @return Result<Page<Personnel>>
	     */
		@PostMapping("/personnelPageMore")
	    public Result<Page<Personnel>> PersonnelMore(Personnel personnel,String pageNum,String pageSize,
	    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
	    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){ //根据情况 再扩张更多查询条件
			return getPersonnelPage(personnel, pageNum, pageSize,startTime,endTime);
	    }
		 /**
	     * 详情(single)
	     * @return Result<Personnel>
	     */
		
		@PostMapping("/personnelSingle")
	    public Result<Personnel> PersonnelSingle(Personnel personnel){
			return getPersonnelSingle(personnel);
	    }
		
		/**
		 *  新增
		 * 
		 * @return Result<String>
		 */
		@PostMapping("/personnelAdd")
		public Result<String> PersonnelAdd(Personnel personnel) {
			
			return getPersonnelAdd(personnel);

		}
		
		 /**
	     * 更新;( 上传/发布)
	     * @return Result<String>
	     */
		@PostMapping("/personnelUpdate")
	    public Result<String> PersonnelUpdate(Personnel personnel){
			return getPersonnelUpdate(personnel);
	       
	    }
	    /**
	     * 删除;
	     * @return Result<String>
	     */
		@PostMapping("/personnelDel")
	    public Result<String> PersonnelDel(Personnel personnel){
			return getPersonnelDel(personnel);
	    }
		
		
		/***
		 * 文件上传
		 * @param file
		 * @param request
		 * @param filePath
		 * @return
		 */
		@PostMapping("/fileUpload")
		public Object fileUpload(@RequestParam(value="fileName" ,required=false) MultipartFile file, HttpServletRequest request){
			//文件位置
			String filePath="ghbg";
			
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
