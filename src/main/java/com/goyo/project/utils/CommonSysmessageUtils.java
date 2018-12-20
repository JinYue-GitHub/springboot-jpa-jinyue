package com.goyo.project.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;

import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Sysmessage;
import com.goyo.project.utils.FileUploadUtils;

@Component
public class CommonSysmessageUtils {

	// *********************************************文章-公共代码************************************************
	/**
	 * 注入仓库
	 */
	@Resource
	private  BaseRepository<Sysmessage, String> baserepository;
	
	private static CommonSysmessageUtils commonSysmessageUtils;
	
	@PostConstruct
    public void init() {
		commonSysmessageUtils = this;
    }

	public  CommonSysmessageUtils() {
		super();
	}


		/**
		 * 添加
		 * 
		 * @param Sysmessage
		 * @return Result<String>
		 */
		public static Result<String> getSysmessageAdd(Sysmessage sysmessage) {

			try {

				getRepository().update(sysmessage);
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
		public  Result<String> getSysmessageUpdate(Sysmessage sysmessage) {
			try {
				getRepository().update(sysmessage);
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
		public  Result<String> getSysmessageDel(Sysmessage sysmessage) {
			try {
				getRepository().delete(sysmessage);
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
		public  Object fileUpload(@RequestParam("fileName") MultipartFile file, HttpServletRequest request,
				String filePath) {

			return FileUploadUtils.fileUpload(file, request, filePath);
		}

		/***
		 * 文件下载
		 */
		public  Object download(String filename, String filePath) throws Exception {

			return FileUploadUtils.listExport(filename, filePath);
		}

		public static BaseRepository<Sysmessage, String>  getRepository() {
	        return commonSysmessageUtils.baserepository;
	    }

		
	
}