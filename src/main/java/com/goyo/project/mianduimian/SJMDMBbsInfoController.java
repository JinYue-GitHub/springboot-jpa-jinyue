package com.goyo.project.mianduimian;

import java.net.URLEncoder;
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import com.goyo.project.common.CommonDataForBbsInfo;
import com.goyo.project.core.Result;
import com.goyo.project.model.Article;
import com.goyo.project.model.BbsInfo;
import com.goyo.project.model.UserInfo;
import com.goyo.project.utils.CommonUtils;
import com.goyo.project.model.BbsInfo;


/**
 * @author: JinYue
 * @ClassName: SJMDMBbsInfoController 
 * @Description:书记面对面
 */
//@JsonInclude(JsonInclude.Include.ALWAYS)
@CrossOrigin //跨域处理
@RestController
@RequestMapping("/sjmdmBbsInfo")
public class SJMDMBbsInfoController extends CommonDataForBbsInfo {
	 /**
     * 指定类别文章查询(显示固定条数)
     * @return Result<Page<BbsInfo>>
     */
	@PostMapping("/bbsInfoPage")
    public Result<Page<BbsInfo>> BbsInfo(BbsInfo bbsInfo,String pageNum,String pageSize){
		
		return getBbsInfoPage(bbsInfo, pageNum, pageSize);
    }
	 /**
     * 指定类别文章查询(更多  带分页)
     * @return Result<Page<BbsInfo>>
     */
	@PostMapping("/bbsInfoPageMore")
	public Result<Page<BbsInfo>> BbsInfoMore(BbsInfo bbsInfo, String pageNum, String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) { // 根据情况
												// 再扩张更多查询条件
		return getBbsInfoPage(bbsInfo, pageNum, pageSize, startTime, endTime);
	}
	
	 /**
     * 文章详情(single)
     * @return Result<BbsInfo>
     */
	
	@PostMapping("/bbsInfoSingle")
    public Result<BbsInfo> BbsInfoSingle(BbsInfo bbsInfo){
		return getBbsInfoSingle(bbsInfo);
    }
	
	 /**
     * 更新;( 新增)
     * @return Result<String>
     */
	@PostMapping("/bbsInfoAdd")
    public Result<String> BbsInfoAdd(BbsInfo bbsInfo){
		return getBbsInfoAdd(bbsInfo);
       
    }
	
	 /**
     * 文章更新;(文章 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/bbsInfoUpdate")
    public Result<String> BbsInfoUpdate(BbsInfo bbsInfo){
		return getBbsInfoUpdate(bbsInfo);
       
    }
    /**
     * 文章删除;
     * @return Result<String>
     */
	@PostMapping("/bbsInfoDel")
    public Result<String> BbsInfoDel(BbsInfo bbsInfo){
		return getBbsInfoDel(bbsInfo);
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
