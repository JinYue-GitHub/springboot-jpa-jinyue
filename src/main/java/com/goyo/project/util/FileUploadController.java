package com.goyo.project.util;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
public class FileUploadController {

	/*
	 * 获取file.html页面
	 */
	@RequestMapping("file")
	public String file() {
		return "/file";
	}

}
