package com.goyo.project.tuanwei.web;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.CommonDataForOneStationAndTwoGroups;
import com.goyo.project.core.Result;
import com.goyo.project.model.OneStationAndTwoGroups;
/**
 * @ClassName: OneStationAndTwoGroupsController 
 * @Description: 一站两群
 */
@RestController
@RequestMapping("/oneStationAndTwoGroups")
public class OneStationAndTwoGroupsController extends CommonDataForOneStationAndTwoGroups{
	
	//R
	//*********************************************一站两群************************************************
	 /**
     * 一站两群查询(single)
     * @return Result<OneStationAndTwoGroups>
     */
	@PostMapping("/oneStationAndTwoGroupsSingle")
    public Result<OneStationAndTwoGroups> oneStationAndTwoGroupsSingle(OneStationAndTwoGroups oneStationAndTwoGroups){
		return getOneStationAndTwoGroupsSingle(oneStationAndTwoGroups);
    }
	
    /**
     * 一站两群查询(all)
     * @return Result<Page<OneStationAndTwoGroups>>
     */
	@PostMapping("/oneStationAndTwoGroupsPage")
    public Result<Page<OneStationAndTwoGroups>> oneStationAndTwoGroups(OneStationAndTwoGroups oneStationAndTwoGroups,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getOneStationAndTwoGroupsPage(oneStationAndTwoGroups, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 一站两群查询(all:更多)
     * @return Result<Page<OneStationAndTwoGroups>>
     */
	@PostMapping("/oneStationAndTwoGroupsPageMore")
    public Result<Page<OneStationAndTwoGroups>> oneStationAndTwoGroupsMore(OneStationAndTwoGroups oneStationAndTwoGroups,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getOneStationAndTwoGroupsPage(oneStationAndTwoGroups, pageNum, pageSize,startTime,endTime);
    }
    /**
     * 一站两群添加;(一站两群 上传/发布)
     * @return Result<String>
     */
	@PostMapping("/oneStationAndTwoGroupsAdd")
    public Result<String> oneStationAndTwoGroupsAdd(OneStationAndTwoGroups oneStationAndTwoGroups){
		return getOneStationAndTwoGroupsAdd(oneStationAndTwoGroups);
       
    }
	
	 /**
     * 一站两群更新;(一站两群 上传/发布)(更新哪个属性就传哪个属性，不更新的就不用传)
     * @return Result<String>
     */
	@PostMapping("/oneStationAndTwoGroupsUpdate")
    public Result<String> oneStationAndTwoGroupsUpdate(OneStationAndTwoGroups oneStationAndTwoGroups){
		return getOneStationAndTwoGroupsUpdate(oneStationAndTwoGroups);
    }

    /**
     * 一站两群删除;
     * @return Result<String>
     */
	@PostMapping("/oneStationAndTwoGroupsDel")
    public Result<String> oneStationAndTwoGroupsDel(OneStationAndTwoGroups oneStationAndTwoGroups){
		return getOneStationAndTwoGroupsDel(oneStationAndTwoGroups);
    }
	
	
}
