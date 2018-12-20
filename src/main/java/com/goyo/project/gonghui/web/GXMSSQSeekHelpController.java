package com.goyo.project.gonghui.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.common.CommonDataForSeekHelp;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
import com.goyo.project.model.SeekHelp;

/**
 * @author: gdd
 * @ClassName: GXMSSQSeekHelpController 
 * @Description: 工会民生诉求
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@RestController
@RequestMapping("/ghmssqSeekHelp")
public class GXMSSQSeekHelpController extends CommonDataForSeekHelp {
	    /**
	     * 指定类别查询(显示固定条数)
	     * @return Result<Page<SeekHelp>>
	     */
		@PostMapping("/seekHelpPage")
	    public Result<Page<SeekHelp>> SeekHelp(SeekHelp seekHelp,String pageNum,String pageSize){
			return getSeekHelpPage(seekHelp, pageNum, pageSize);
	    }
		 /**
	     * 指定类别查询(更多  带分页)
	     * @return Result<Page<SeekHelp>>
	     */
		
		
		
		@PostMapping("/seekHelpPageMore")
	    public Result<Page<SeekHelp>> SeekHelpMore(SeekHelp seekHelp,String pageNum,String pageSize,
	    		@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime){
			return getSeekHelpPage(seekHelp,pageNum, pageSize, startTime, endTime);
	    }
		 /**
	     * 详情(single)
	     * @return Result<SeekHelp>
	     */
		
		@PostMapping("/seekHelpSingle")
	    public Result<SeekHelp> SeekHelpSingle(SeekHelp seekHelp){
			return getSeekHelpSingle(seekHelp);
	    }
		
		/**
	     * 更新;( 新增)
	     * @return Result<String>
	     */
		@PostMapping("/seekHelpAdd")
	    public Result<String> SeekHelpAdd(SeekHelp seekHelp){
			return getSeekHelpAdd(seekHelp);
	       
	    }
		
		
		 /**
	     * 更新;(文章 上传/发布)
	     * @return Result<String>
	     */
		@PostMapping("/seekHelpUpdate")
	    public Result<String> SeekHelpUpdate(SeekHelp seekHelp){
			return getSeekHelpUpdate(seekHelp);
	       
	    }
	    /**
	     * 删除;
	     * @return Result<String>
	     */
		@PostMapping("/seekHelpDel")
	    public Result<String> SeekHelpDel(SeekHelp seekHelp){
			return getSeekHelpDel(seekHelp);
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
			String filePath="ghmssq";
			
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
