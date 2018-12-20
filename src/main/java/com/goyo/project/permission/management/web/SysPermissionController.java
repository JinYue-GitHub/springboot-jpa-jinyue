package com.goyo.project.permission.management.web;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.SysPermission;
import com.goyo.project.model.UserInfo;
import com.goyo.project.permission.management.dao.SysPermissionDao;

@RestController
@RequestMapping("/permissionInfo")
public class SysPermissionController {

	@Resource
	private SysPermissionDao sysPermissionDao;
	
    /**
     * 权限查询
     * @return
     */
	@PostMapping("/permissionList")
    @RequiresPermissions("permission:view")//权限管理;
    public Result<Page<SysPermission> > permissionInfo(SysPermission sysPermission,String pageNum,String pageSize){
		Page<SysPermission> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = sysPermissionDao.findAll(pageable);
		}else{
			page = sysPermissionDao.findAll(pageable);
		}
        return ResultGenerator.genSuccessResult(page);
    }

    /**
     * 权限添加;
     * @return
     */
	@PostMapping("/permissionAdd")
//    @RequiresPermissions("permission:add")//权限管理;
    public Result<String> permissionAdd(){
		
//		"ID", "AVAILABLE", "NAME", "PARENT_ID", "PARENT_IDS", "PERMISSION", "RESOURCE_TYPE", "URL")
//		('1', '0', 		'用户管理', '0', 			'0/', 	'userInfo:view', 'menu', 		'userInfo/userList');
//		sysPermission:add sysPermission:del
		
		//添加根节点菜单
		/*SysPermission sysPermission1 = new SysPermission();
		sysPermission1.setAvailable((short) 1);
		sysPermission1.setName("党群工作");
		sysPermission1.setParentId(0L);
		sysPermission1.setParentIds("0/");
		
		sysPermission1.setPermission("view");
		sysPermission1.setResourceType("menu");
//		sysPermission1.setUrl("permissionInfo/userList");
		sysPermissionDao.save(sysPermission1);
		sysPermission1.setIds(sysPermission1.getParentIds()+sysPermission1.getId()+"/");
		sysPermissionDao.save(sysPermission1);*/
		
		SysPermission sysPermission1 = sysPermissionDao.findById(421).get();
		
		SysPermission sysPermission2 = new SysPermission();
		sysPermission2.setAvailable((short) 1);
		sysPermission2.setName("人员管理");
		sysPermission2.setParentId(sysPermission1.getId().longValue());
		sysPermission2.setParentIds(sysPermission1.getParentIds()+sysPermission2.getParentId()+"/");
		sysPermission2.setPermission("view"); //single page pageMore  add update del 
		sysPermission2.setResourceType("menu");
		sysPermission2.setUrl("/authorization/permissionConfigList");
		sysPermissionDao.save(sysPermission2);
		sysPermission2.setIds(sysPermission2.getParentIds()+sysPermission2.getId()+"/");
		sysPermissionDao.save(sysPermission2);
		
		
		
		
		//党务管理
		/*SysPermission sysPermission2 = new SysPermission();
		sysPermission2.setAvailable((short) 1);
		sysPermission2.setName("上报资料");
		sysPermission2.setParentId(sysPermission1.getId().longValue());
		sysPermission2.setParentIds(sysPermission1.getParentIds()+sysPermission2.getParentId()+"/");
		sysPermission2.setPermission("view:single"); //single page pageMore  add update del 
		sysPermission2.setResourceType("button");
		sysPermission2.setUrl("/lzjsArticle/articleSingle");
		sysPermissionDao.save(sysPermission2);
		sysPermission2.setIds(sysPermission2.getParentIds()+sysPermission2.getId()+"/");
		sysPermissionDao.save(sysPermission2);
		
		
		SysPermission sysPermission6 = new SysPermission();
		sysPermission6.setAvailable((short) 1);
		sysPermission6.setName("上报资料");
		sysPermission6.setParentId(sysPermission1.getId().longValue());
		sysPermission6.setParentIds(sysPermission1.getParentIds()+sysPermission6.getParentId()+"/");
		sysPermission6.setPermission("view:page"); //single Page PageMore  Add Update Del 
		sysPermission6.setResourceType("menu");
		sysPermission6.setUrl("/lzjsArticle/articlePage");
		sysPermissionDao.save(sysPermission6);
		sysPermission6.setIds(sysPermission6.getParentIds()+sysPermission6.getId()+"/");
		sysPermissionDao.save(sysPermission6);
		
		
		SysPermission sysPermission7 = new SysPermission();
		sysPermission7.setAvailable((short) 1);
		sysPermission7.setName("上报资料");
		sysPermission7.setParentId(sysPermission1.getId().longValue());
		sysPermission7.setParentIds(sysPermission1.getParentIds()+sysPermission7.getParentId()+"/");
		sysPermission7.setPermission("view:pageMore"); //single Page PageMore  Add Update Del 
		sysPermission7.setResourceType("button");
		sysPermission7.setUrl("/lzjsArticle/articlePageMore");
		sysPermissionDao.save(sysPermission7);
		sysPermission7.setIds(sysPermission7.getParentIds()+sysPermission7.getId()+"/");
		sysPermissionDao.save(sysPermission7);
		
		
		
		SysPermission sysPermission5 = new SysPermission();
		sysPermission5.setAvailable((short) 1);
		sysPermission5.setName("上报资料");
		sysPermission5.setParentId(sysPermission1.getId().longValue());
		sysPermission5.setParentIds(sysPermission1.getParentIds()+sysPermission5.getParentId()+"/");
		sysPermission5.setPermission("add");
		sysPermission5.setResourceType("button");
		sysPermission5.setUrl("/lzjsArticle/articleAdd");
		sysPermissionDao.save(sysPermission5);
		sysPermission5.setIds(sysPermission5.getParentIds()+sysPermission5.getId()+"/");
		sysPermissionDao.save(sysPermission5);
		
		SysPermission sysPermission3 = new SysPermission();
		sysPermission3.setAvailable((short) 1);
		sysPermission3.setName("上报资料");
		sysPermission3.setParentId(sysPermission1.getId().longValue());
		sysPermission3.setParentIds(sysPermission1.getParentIds()+sysPermission3.getParentId()+"/");
		sysPermission3.setPermission("update");
		sysPermission3.setResourceType("button");
		sysPermission3.setUrl("/lzjsArticle/articleUpdate");
		sysPermissionDao.save(sysPermission3);
		sysPermission3.setIds(sysPermission3.getParentIds()+sysPermission3.getId()+"/");
		sysPermissionDao.save(sysPermission3);
		
		SysPermission sysPermission4 = new SysPermission();
		sysPermission4.setAvailable((short) 1);
		sysPermission4.setName("上报资料");
		sysPermission4.setParentId(sysPermission1.getId().longValue());
		sysPermission4.setParentIds(sysPermission1.getParentIds()+sysPermission4.getParentId()+"/");
		sysPermission4.setPermission("del");
		sysPermission4.setResourceType("button");
		sysPermission4.setUrl("/lzjsArticle/articleDel");
		sysPermissionDao.save(sysPermission4);
		sysPermission4.setIds(sysPermission4.getParentIds()+sysPermission4.getId()+"/");
		sysPermissionDao.save(sysPermission4);*/
		//******************************三会一课*********************************
		//三会一课 C
		/*SysPermission sysPermission4 = new SysPermission();
		sysPermission4.setAvailable((short) 1);
		sysPermission4.setName("上报资料");
		sysPermission4.setParentId(sysPermission2.getId().longValue());
		sysPermission4.setParentIds(sysPermission2.getParentIds()+sysPermission4.getParentId()+"/");
		sysPermission4.setPermission("aticle:add");
		sysPermission4.setResourceType("button");
		sysPermission4.setUrl("dwglArticle/shykArticleAdd");
		sysPermissionDao.save(sysPermission4);
		sysPermission4.setIds(sysPermission4.getParentIds()+sysPermission4.getId()+"/");
		sysPermissionDao.save(sysPermission4);
		//三会一课 R
		SysPermission sysPermission3 = new SysPermission();
		sysPermission3.setAvailable((short) 1);
		sysPermission3.setName("三会一课");
		sysPermission3.setParentId(sysPermission2.getId().longValue());
		sysPermission3.setParentIds(sysPermission2.getParentIds()+sysPermission3.getParentId()+"/");
		sysPermission3.setPermission("aticle:view");
		sysPermission3.setResourceType("button");
		sysPermission3.setUrl("dwglArticle/shykArticleSingle");
		sysPermissionDao.save(sysPermission3);
		sysPermission3.setIds(sysPermission3.getParentIds()+sysPermission3.getId()+"/");
		sysPermissionDao.save(sysPermission3);
		
		SysPermission sysPermission7 = new SysPermission();
		sysPermission7.setAvailable((short) 1);
		sysPermission7.setName("三会一课");
		sysPermission7.setParentId(sysPermission2.getId().longValue());
		sysPermission7.setParentIds(sysPermission2.getParentIds()+sysPermission7.getParentId()+"/");
		sysPermission7.setPermission("aticle:view");
		sysPermission7.setResourceType("menu");
		sysPermission7.setUrl("dwglArticle/shykArticlePage");
		sysPermissionDao.save(sysPermission7);
		sysPermission7.setIds(sysPermission7.getParentIds()+sysPermission7.getId()+"/");
		sysPermissionDao.save(sysPermission7);
		//三会一课 U
		SysPermission sysPermission6 = new SysPermission();
		sysPermission6.setAvailable((short) 1);
		sysPermission6.setName("三会一课");
		sysPermission6.setParentId(sysPermission2.getId().longValue());
		sysPermission6.setParentIds(sysPermission2.getParentIds()+sysPermission6.getParentId()+"/");
		sysPermission6.setPermission("update");
		sysPermission6.setResourceType("button");
		sysPermission6.setUrl("/dwglArticle/shykArticleUpdate");
		sysPermissionDao.save(sysPermission6);
		sysPermission6.setIds(sysPermission6.getParentIds()+sysPermission6.getId()+"/");
		sysPermissionDao.save(sysPermission6);
		//三会一课 D
		SysPermission sysPermission5 = new SysPermission();
		sysPermission5.setAvailable((short) 1);
		sysPermission5.setName("三会一课");
		sysPermission5.setParentId(sysPermission2.getId().longValue());
		sysPermission5.setParentIds(sysPermission2.getParentIds()+sysPermission5.getParentId()+"/");
		sysPermission5.setPermission("aticle:del");
		sysPermission5.setResourceType("button");
		sysPermission5.setUrl("/dwglArticle/shykArticleDel");
		sysPermissionDao.save(sysPermission5);
		sysPermission5.setIds(sysPermission5.getParentIds()+sysPermission5.getId()+"/");
		sysPermissionDao.save(sysPermission5);*/
		
		
		//******************************特色活动*********************************
        return ResultGenerator.genSuccessResult();
        
    }
	
	/**
     * 权限更新;
     * @return
     */
	@PostMapping("/permissionUpdate")
    @RequiresPermissions("permission:update")//权限管理;
    public Result<String> permissionUpdate(SysPermission sysPermission){
		sysPermissionDao.save(sysPermission);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 权限删除;
     * @return
     */
	@PostMapping("/permissionDel")
    @RequiresPermissions("permission:del")//权限管理;
    public Result<String> permissionDel(SysPermission sysPermission){
		sysPermissionDao.deleteById(sysPermission.getId());
        return ResultGenerator.genSuccessResult();
    }
}