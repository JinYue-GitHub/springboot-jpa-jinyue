package com.goyo.project.organization.management.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.SysRole;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.OrganizationDao;
import com.goyo.project.role.management.dao.SysRoleDao;
import com.goyo.project.utils.BeanUtils;
import com.goyo.project.utils.CommonUtils;
@RestController
@RequestMapping("/organization")
public class OrganizationController {
	
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private SysRoleDao sysRoleDao;
	//
	
	private Organization recursionFn(List<Organization> list, Organization node) {
        List<Organization> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            Iterator<Organization> it = childList.iterator();
            while (it.hasNext()) {
//            	returnList.add(node);
            	node.setSubOrganization(childList);
            	Organization n = (Organization) it.next();
                recursionFn(list, n);
            }
            return node;
        } else {
        	node.setSubOrganization(childList);
        	return node;
        }
    }
     
    // 得到子节点列表
    private List<Organization> getChildList(List<Organization> list, Organization node) {
        List<Organization> nodeList = new ArrayList<Organization>();
        Iterator<Organization> it = list.iterator();
        while (it.hasNext()) {
        	Organization n = (Organization) it.next();
            if (n.getParentId() == node.getId()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
 
    // 判断是否有子节点
    private boolean hasChild(List<Organization> list, Organization node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }
	
	public static int getCount(String str,String scan){
		if(StringUtils.isEmpty(str)||StringUtils.isEmpty(scan)){
			return 0;
		}else{
			int count = 0;
	        int index = 0;
	        while(((index = str.indexOf(scan)) != -1)){
	            //想个办法截取字符串中查找字符,并将查找当前匹配字符之后的字符串重新
	            //赋值给字符串
	            str = str.substring(index+1);
	            count++;
	        }
	        return count;
		}
		
       
    }
	
	 /**
     * 完善信息部门接口(包含子节点)
     * @return Result<Organization>
     */
	@PostMapping("/organizationRootWithSub")
//  @RequiresPermissions("organization:view")//权限管理;
    public Result<Organization> organizationRootWithSub(Organization organization){
		UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
		List<Organization> findAll = organizationDao.findAll();
		Organization organizationRoot = null;
		if(Optional.ofNullable(organization.getId()).isPresent()){//查指定根节点
			organizationRoot = organizationDao.findById(organization.getId()).get();
		}else{//查根节点下的所有节点
			List<Organization> collect = findAll.stream().filter(u-> getCount(u.getIds(),"/")==2).collect(Collectors.toList());
			organizationRoot = collect.get(0);
		}
		Organization recursionFn = recursionFn(findAll,organizationRoot);
        return ResultGenerator.genSuccessResult(recursionFn);
    }
	
	
	
	 /**
     * 组织查询(single)
     * @return Result<Organization>
     */
	@PostMapping("/organizationSingle")
//  @RequiresPermissions("organization:view")//权限管理;
    public Result<Organization> organizationSingle(Organization organization){
        return ResultGenerator.genSuccessResult(organizationDao.findById(organization.getId()).get());
    }
    /**
     * 组织查询(all)
     * @return Result<List<Organization>>
     */
	@PostMapping("/organizationList")
//    @RequiresPermissions("organization:view")//权限管理;
    public Result<List<Organization>> organization(Organization organization){
        return ResultGenerator.genSuccessResult(organizationDao.findAll());
    }

    /**
     * 组织添加;
     * @return
     */
	@PostMapping("/organizationAdd")
	@Transactional
    public Result<String> organizationAdd(Organization organization){
		try {
			organizationDao.save(organization);
			if(organization.getParentId()==0){//说明是创建根节点
				organization.setParentIds(organization.getParentId()+"/");
				organization.setIds(organization.getParentIds()+organization.getId()+"/");
			}else{//子节点
				organization.setParentIds(organizationDao.findById(organization.getParentId()).get().getIds());
				organization.setIds(organization.getParentIds()+organization.getId()+"/");
			}
			organizationDao.save(organization);
//			organization.getParentId();
//			
//			SysRole sysRole = new SysRole();
//			sysRole.setAvailable((short) 1);
//			sysRole.setDescription(organization.getIds());
//			sysRole.setRole(organization.getName());
//			sysRoleDao.save(sysRole);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
       
    }
	
	 /**
     * 组织修改;
     * @return
     */
	@PostMapping("/organizationUpdate")
	@Transactional
    public Result<String> organizationUpdate(Organization organization){
		try {
			Organization organizationDB = organizationDao.findById(organization.getId()).get();
			BeanUtils.copyProperties(organization, organizationDB);
			organizationDao.save(organization);
			return ResultGenerator.genSuccessResult();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
    }

    /**
     * 组织删除;
     * @return
     */
	@PostMapping("/organizationDel")
//    @RequiresPermissions("organization:del")//权限管理;
    public Result<String> organizationDel(Organization organization){
		try {
			organizationDao.deleteById(organization.getId());
			return ResultGenerator.genSuccessResult("organizationDel");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
    }
}
