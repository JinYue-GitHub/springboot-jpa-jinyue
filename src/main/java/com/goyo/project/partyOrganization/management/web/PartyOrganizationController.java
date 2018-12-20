package com.goyo.project.partyOrganization.management.web;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.PartyOrganization;
import com.goyo.project.partyOrganization.management.dao.PartyOrganizationDao;
import com.goyo.project.utils.BeanUtils;
@RestController
@RequestMapping("/partyPartyOrganization")
public class PartyOrganizationController {
	
	@Resource
	private PartyOrganizationDao partyOrganizationDao;
	
	private PartyOrganization recursionFn(List<PartyOrganization> list, PartyOrganization node) {
        List<PartyOrganization> childList = getChildList(list, node);// 得到子节点列表
        if (hasChild(list, node)) {// 判断是否有子节点
            Iterator<PartyOrganization> it = childList.iterator();
            while (it.hasNext()) {
            	node.setSubPartyOrganization(childList);
            	PartyOrganization n = (PartyOrganization) it.next();
                recursionFn(list, n);
            }
            return node;
        } else {
        	node.setSubPartyOrganization(childList);
        	return node;
        }
    }
     
    // 得到子节点列表
    private List<PartyOrganization> getChildList(List<PartyOrganization> list, PartyOrganization node) {
        List<PartyOrganization> nodeList = new ArrayList<PartyOrganization>();
        Iterator<PartyOrganization> it = list.iterator();
        while (it.hasNext()) {
        	PartyOrganization n = (PartyOrganization) it.next();
            if (n.getParentId() == node.getId()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }
 
    // 判断是否有子节点
    private boolean hasChild(List<PartyOrganization> list, PartyOrganization node) {
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
	@PostMapping("/partyOrganizationRootWithSub")
//  @RequiresPermissions("organization:view")//权限管理;
    public Result<PartyOrganization> partyOrganizationRootWithSub(PartyOrganization partyOrganization){
		
		List<PartyOrganization> findAll = partyOrganizationDao.findAll();
		
		List<PartyOrganization> collect = findAll
		.stream().filter(u-> getCount(u.getIds(),"/")==2).collect(Collectors.toList());
		
		PartyOrganization partyOrganization2 = collect.get(0);
		
		PartyOrganization recursionFn = recursionFn(findAll,partyOrganization2);
		
        return ResultGenerator.genSuccessResult(recursionFn);
    }

	 /**
     * 组织查询(single)
     * @return Result<PartyOrganization>
     */
	@PostMapping("/partyPartyOrganizationSingle")
    public Result<PartyOrganization> partyOrganizationSingle(PartyOrganization partyOrganization){
        return ResultGenerator.genSuccessResult(partyOrganizationDao.findById(partyOrganization.getId()).get());
    }
    /**
     * 组织查询(all)
     * @return Result<List<PartyOrganization>>
     */
	@PostMapping("/partyPartyOrganizationList")
    public Result<List<PartyOrganization>> partyOrganization(PartyOrganization partyOrganization){
        return ResultGenerator.genSuccessResult(partyOrganizationDao.findAll());
    }

    /**
     * 组织添加;
     * @return
     */
	@PostMapping("/partyPartyOrganizationAdd")
	@Transactional
    public Result<String> partyOrganizationAdd(PartyOrganization partyOrganization){
		try {
			partyOrganizationDao.save(partyOrganization);
			if(partyOrganization.getParentId()==0){//说明是创建根节点
				partyOrganization.setParentIds(partyOrganization.getParentId()+"/");
				partyOrganization.setIds(partyOrganization.getParentIds()+partyOrganization.getId()+"/");
			}else{//子节点
				partyOrganization.setParentIds(partyOrganizationDao.findById(partyOrganization.getParentId()).get().getIds());
				partyOrganization.setIds(partyOrganization.getParentIds()+partyOrganization.getId()+"/");
			}
			partyOrganizationDao.save(partyOrganization);
			partyOrganization.getParentId();
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
	@PostMapping("/partyPartyOrganizationUpdate")
	@Transactional
    public Result<String> partyPartyOrganizationUpdate(PartyOrganization partyOrganization){
		try {
			PartyOrganization partyOrganizationDB = partyOrganizationDao.findById(partyOrganization.getId()).get();
			BeanUtils.copyProperties(partyOrganization, partyOrganizationDB);
			partyOrganizationDao.save(partyOrganizationDB);
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
	@PostMapping("/partyPartyOrganizationDel")
    public Result<String> partyOrganizationDel(PartyOrganization partyOrganization){
		try {
			partyOrganizationDao.deleteById(partyOrganization.getId());
			return ResultGenerator.genSuccessResult("partyOrganizationDel");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult(e.getMessage());
		}
    }
}
