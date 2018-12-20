package com.goyo.project.utils;

import java.io.File;

import java.io.IOException;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.goyo.project.core.ResultGenerator;


public class FileUploadUtils {
	
	
	
	/**
	 * 文件存储路径
	 */
	static String path = "/usr/local/party-9095/upload";
	/*static String path = "d:";*/
	/***
	 * 文件上传
	 * @param file
	 * @param request
	 * @param filePath
	 * @return
	 */
	
	public static Object fileUpload(MultipartFile file, HttpServletRequest request,String filePath) {

		if (file.isEmpty()) {
			return ResultGenerator.genFailResult("没有文件");
		}
		String fileName = file.getOriginalFilename();
		int size = (int) file.getSize();
		System.out.println(fileName + "-->" + size);

		
		
		
		UUID uuid = UUID.randomUUID();
		String paths=path+"/"+filePath + "/"+uuid+fileName.substring(fileName.indexOf("."));
		System.out.println(paths);
		File dest = new File(paths);
		//dest.renameTo(new File(paths));
		
		if (!dest.getParentFile().exists()) { // 判断文件父目录是否存在
			dest.getParentFile().mkdir();
		}
		try {
			file.transferTo(dest); // 保存文件
			Map<String, String> map=new HashMap<String, String>();
			map.put("fileName", fileName);
			map.put("filePath",paths);
			
			return ResultGenerator.genSuccessResult(map);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
	}



	
	/**
	 * 下载
	 * 
	 * @param proNo
	 * @param filename
	 * @return
	 * @throws Exception
	 */

	public static Object listExport(String filename,String filePath) throws Exception {
		/*filename="党政工作菜单.jar";
		filePath="D:/test";*/
		// 截取文件扩展名

		File file = new File(filePath);
		if (file.exists()) {
			return export(file, filename);
		}
		return ResultGenerator.genFailResult("文件丢失");
	}

	public static ResponseEntity<FileSystemResource> export(File file, String filename) throws Exception {
		System.out.println(filename);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
	}

}
