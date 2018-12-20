package com.goyo.project.data.dictionary.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultCode;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.data.dictionary.service.DataDictionaryGroupsService;
import com.goyo.project.model.DataDictionaryGroups;
/**
 * @author: JinYue
 * @ClassName: DataDictionaryGroupsController 
 * @Description: 数据字典组CRUD
 */
@RestController
@RequestMapping("/dataDictionaryGroups")
public class DataDictionaryGroupsController {
	
	@Resource
	private DataDictionaryGroupsService dataDictionaryGroupsService;

    /**
     * 数据字典组查询(single)
     * @return Result<DataDictionaryGroups>
     */
	@PostMapping("/dataDictionaryGroupsSingle")
//    @RequiresPermissions("dataDictionaryGroups:view")//权限管理;
    public Result<DataDictionaryGroups> userSingle(DataDictionaryGroups dataDictionaryGroups){
        return ResultGenerator.genSuccessResult(dataDictionaryGroupsService.findById(dataDictionaryGroups.getGroupCode()));
    }
	
	/**
     * 数据字典组查询(all)
     * @return Result<List<DataDictionaryGroups>>
     */
	@PostMapping("/dataDictionaryGroupsList")
//    @RequiresPermissions("dataDictionaryGroups:view")//权限管理;
    public Result<Page<DataDictionaryGroups>> userList(DataDictionaryGroups dataDictionaryGroups,String pageNum,String pageSize){
		Page<DataDictionaryGroups> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = dataDictionaryGroupsService.findAll(dataDictionaryGroups,pageable);
		}else if(
					StringUtils.isBlank(pageNum)&&
					StringUtils.isBlank(pageSize)
				)
		{
			page = dataDictionaryGroupsService.findAll(pageable);
		}else{
			return new Result<Page<DataDictionaryGroups>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
		
    }

    /**
     * 数据字典组添加;
     * @return
     */
	@PostMapping("/dataDictionaryGroupsAdd")
//    @RequiresPermissions("dataDictionaryGroups:add")//权限管理;
    public Result<String> dataDictionaryGroupsAdd(DataDictionaryGroups dataDictionaryGroups){
		DataDictionaryGroups dataDictionaryGroups1 = new DataDictionaryGroups();
		dataDictionaryGroups1.setGroupName("政治面貌");
		dataDictionaryGroupsService.save(dataDictionaryGroups1);
		return ResultGenerator.genSuccessResult();
    }
	 /**
     * 数据字典修改;
     * @return
     */
	@PostMapping("/dataDictionaryGroupsUpdate")
//    @RequiresPermissions("dataDictionaryGroups:add")//权限管理;
    public Result<String> dataDictionaryGroupsUpdate(DataDictionaryGroups dataDictionaryGroups){
		DataDictionaryGroups dataDictionaryGroups1 = new DataDictionaryGroups();
		dataDictionaryGroups1.setGroupName("政治面貌");
		dataDictionaryGroupsService.save(dataDictionaryGroups1);
		return ResultGenerator.genSuccessResult();
    }

    /**
     * 数据字典组删除;
     * @return
     */
	@PostMapping("/dataDictionaryGroupsDel")
//    @RequiresPermissions("dataDictionaryGroups:del")//权限管理;
    public Result<String> userDel(){
        return ResultGenerator.genSuccessResult("dataDictionaryGroupsDel");
    }
}