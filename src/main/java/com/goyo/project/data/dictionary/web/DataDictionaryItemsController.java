package com.goyo.project.data.dictionary.web;

import java.util.List;
import java.util.stream.Stream;

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
import com.goyo.project.data.dictionary.service.DataDictionaryItemsService;
import com.goyo.project.model.DataDictionaryGroups;
import com.goyo.project.model.DataDictionaryItems;
/**
 * @author: JinYue
 * @ClassName: DataDictionaryItemsController 
 * @Description: 数据字典项CRUD
 */
@RestController
@RequestMapping("/dataDictionaryItems")
public class DataDictionaryItemsController {
	
	@Resource
	private DataDictionaryGroupsService dataDictionaryGroupsService;
	
	@Resource
	private DataDictionaryItemsService dataDictionaryItemsService;

    /**
     * 数据字典项查询(single)
     * @return Result<DataDictionaryItems>
     */
	@PostMapping("/dataDictionaryItemsSingle")
//    @RequiresPermissions("dataDictionaryItems:view")//权限管理;
    public Result<DataDictionaryItems> userSingle(DataDictionaryItems dataDictionaryItems){
        return ResultGenerator.genSuccessResult(dataDictionaryItemsService.findById(dataDictionaryItems.getDataitemCode()));
    }
	
	/**
     * 数据字典项查询(all)
     * @return Result<List<DataDictionaryItems>>
     */
	@PostMapping("/dataDictionaryItemsList")
//    @RequiresPermissions("dataDictionaryItems:view")//权限管理;
    public Result<Page<DataDictionaryItems>> userList(DataDictionaryItems dataDictionaryItems,String pageNum,String pageSize){
		Page<DataDictionaryItems> page = null;
		Pageable pageable = null;
		if(StringUtils.isNoneBlank(pageNum)&&StringUtils.isNoneBlank(pageSize)){
			int pageNumInt = Integer.parseInt(pageNum);
			int pageSizeInt = Integer.parseInt(pageSize);
			pageable = new PageRequest(pageNumInt,pageSizeInt);
			page = dataDictionaryItemsService.findAll(dataDictionaryItems,pageable);
		}else if(
					StringUtils.isBlank(pageNum)&&
					StringUtils.isBlank(pageSize)
				)
		{
			page = dataDictionaryItemsService.findAll(pageable);
		}else{
			return new Result<Page<DataDictionaryItems>>()
	                .setCode(ResultCode.FAIL)
	                .setMessage("分页查询失败!");
		}
		return ResultGenerator.genSuccessResult(page);
		
    }

    /**
     * 数据字典项添加;
     * @return
     */
	@PostMapping("/dataDictionaryItemsAdd")
//    @RequiresPermissions("dataDictionaryItems:add")//权限管理;
    public Result<String> dataDictionaryItemsAdd(DataDictionaryItems dataDictionaryItems){
		
		DataDictionaryGroups dg = dataDictionaryGroupsService.findByGroupName("政治面貌");
		
		List<DataDictionaryItems> dataDictionaryItems2List = dg.getDataDictionaryItems();
		
		Stream<String> map = dataDictionaryItems2List.stream().map(m->m.getDataitemName());
		System.out.println(map);
		for(DataDictionaryItems dd:dataDictionaryItems2List){
			System.out.println(dd.getDataitemName());
		}
		
		DataDictionaryItems dataDictionaryItems1 = new DataDictionaryItems();
		DataDictionaryItems dataDictionaryItems2 = new DataDictionaryItems();
		DataDictionaryItems dataDictionaryItems3 = new DataDictionaryItems();
		DataDictionaryItems dataDictionaryItems4 = new DataDictionaryItems();
		DataDictionaryItems dataDictionaryItems5 = new DataDictionaryItems();
		
		dataDictionaryItems1.setDataitemName("党员");
		dataDictionaryItems1.setDataDictionaryGroups(dg);
		
		dataDictionaryItems2.setDataitemName("预备党员");
		dataDictionaryItems2.setDataDictionaryGroups(dg);
		
		dataDictionaryItems3.setDataitemName("积极分子");
		dataDictionaryItems3.setDataDictionaryGroups(dg);
		
		dataDictionaryItems4.setDataitemName("团员");
		dataDictionaryItems4.setDataDictionaryGroups(dg);
		
		dataDictionaryItems5.setDataitemName("群众");
		dataDictionaryItems5.setDataDictionaryGroups(dg);
		
		dataDictionaryItemsService.save(dataDictionaryItems1);
		dataDictionaryItemsService.save(dataDictionaryItems2);
		dataDictionaryItemsService.save(dataDictionaryItems3);
		dataDictionaryItemsService.save(dataDictionaryItems4);
		dataDictionaryItemsService.save(dataDictionaryItems5);
		
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 数据字典项删除;
     * @return
     */
	@PostMapping("/dataDictionaryItemsDel")
//    @RequiresPermissions("dataDictionaryItems:del")//权限管理;
    public Result<String> userDel(){
        return ResultGenerator.genSuccessResult("dataDictionaryItemsDel");
    }
}