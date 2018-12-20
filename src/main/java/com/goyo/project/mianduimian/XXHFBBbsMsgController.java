package com.goyo.project.mianduimian;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.goyo.project.common.CommonDataForBbsMsg;
import com.goyo.project.common.CommonDataForBbsMsg;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
import com.goyo.project.model.BbsMsg;

/**
 * @author: gdd
 * @ClassName: XXHFBBbsMsgController 
 * @Description: 消息回复
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@CrossOrigin //跨域处理
@RestController
@RequestMapping("/xxhfBbsMsg")
public class XXHFBBbsMsgController extends CommonDataForBbsMsg {
	    /**
	     * 指定类别文章查询(显示固定条数)
	     * @return Result<Page<BbsMsg>>
	     */
		@PostMapping("/bbsMsgPage")
	    public Result<Page<BbsMsg>> BbsMsg(BbsMsg bbsMsg,String pageNum,String pageSize){
			return getBbsMsgPage(bbsMsg, pageNum, pageSize);
	    }
		 /**
	     * 指定类别文章查询(更多  带分页)
	     * @return Result<Page<BbsMsg>>
	     */
		@PostMapping("/bbsMsgPageMore")
	    public Result<Page<BbsMsg>> BbsMsgMore(BbsMsg bbsMsg, String pageNum, String pageSize,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
				@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 根据情况
																				// 再扩张更多查询条件
			return getBbsMsgPage(bbsMsg, pageNum, pageSize, startTime, endTime);
		}
		
		
		 /**
	     * 文章详情(single)
	     * @return Result<BbsMsg>
	     */
		
		@PostMapping("/bbsMsgSingle")
	    public Result<BbsMsg> BbsMsgSingle(BbsMsg bbsMsg){
			return getBbsMsgSingle(bbsMsg);
	    }
		
		 /**
	     * 更新;( 新增)
	     * @return Result<String>
	     */
		@PostMapping("/bbsMsgAdd")
	    public Result<String> BbsMsgAdd(BbsMsg bbsMsg){
			return getBbsMsgAdd(bbsMsg);
	       
	    }
		
		 /**
	     * 文章更新;(文章 上传/发布)
	     * @return Result<String>
	     */
		@PostMapping("/bbsMsgUpdate")
	    public Result<String> BbsMsgUpdate(BbsMsg bbsMsg){
			return getBbsMsgUpdate(bbsMsg);
	       
	    }
	    /**
	     * 文章删除;
	     * @return Result<String>
	     */
		@PostMapping("/bbsMsgDel")
	    public Result<String> BbsMsgDel(BbsMsg bbsMsg){
			return getBbsMsgDel(bbsMsg);
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
			String filePath="ghzx";
			
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
