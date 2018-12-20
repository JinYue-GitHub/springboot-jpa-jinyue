package com.goyo.project.jiwei.web;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.CommonDataForPetitionReportRelevantInformation;
import com.goyo.project.core.Result;
import com.goyo.project.model.PetitionReportRelevantInformation;

/**
 * @author: JinYue
 * @ClassName: PetitionReportRelevantInformationRelevantInformationController 
 * @Description: 信访举报信息相关信息相关
 */
@RestController
@RequestMapping("/petitionReportRelevantInformation")
public class PetitionReportRelevantInformationController extends CommonDataForPetitionReportRelevantInformation {
	
	
	//*********************************************信访举报信息相关CRUD************************************************
	 /**
     * 信访举报信息相关查询(single)
     * @return Result<PetitionReportRelevantInformation>
     */
	@PostMapping("/petitionReportRelevantInformationSingle")
    public Result<PetitionReportRelevantInformation> petitionReportRelevantInformationSingle(PetitionReportRelevantInformation petitionReportRelevantInformation){
		return getPetitionReportRelevantInformationSingle(petitionReportRelevantInformation);
    }
	
    /**
     * 信访举报信息相关查询(all)
     * @return Result<Page<PetitionReportRelevantInformation>>
     */
	@PostMapping("/petitionReportRelevantInformationPage")
    public Result<Page<PetitionReportRelevantInformation>> petitionReportRelevantInformation(PetitionReportRelevantInformation petitionReportRelevantInformation,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getPetitionReportRelevantInformationPage(petitionReportRelevantInformation, pageNum, pageSize,startTime,endTime);
    }
	 /**
     * 信访举报信息相关查询(all:更多)
     * @return Result<Page<PetitionReportRelevantInformation>>
     */
	@PostMapping("/petitionReportRelevantInformationPageMore")
    public Result<Page<PetitionReportRelevantInformation>> petitionReportRelevantInformationMore(PetitionReportRelevantInformation petitionReportRelevantInformation,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getPetitionReportRelevantInformationPage(petitionReportRelevantInformation, pageNum, pageSize,startTime,endTime);
    }
    /**
     * 信访举报信息相关添加;
     * @return Result<String>
     */
	@PostMapping("/petitionReportRelevantInformationAdd")
    public Result<String> petitionReportRelevantInformationAdd(PetitionReportRelevantInformation petitionReportRelevantInformation){
		return getPetitionReportRelevantInformationAdd(petitionReportRelevantInformation);
       
    }
	
	 /**
     * 信访举报信息相关更新;
     * @return Result<String>
     */
	@PostMapping("/petitionReportRelevantInformationUpdate")
    public Result<String> petitionReportRelevantInformationUpdate(PetitionReportRelevantInformation petitionReportRelevantInformation){
		return getPetitionReportRelevantInformationUpdate(petitionReportRelevantInformation);
       
    }

    /**
     * 信访举报信息相关删除;
     * @return Result<String>
     */
	@PostMapping("/petitionReportRelevantInformationDel")
    public Result<String> petitionReportRelevantInformationDel(PetitionReportRelevantInformation petitionReportRelevantInformation){
		return getPetitionReportRelevantInformationDel(petitionReportRelevantInformation);
    }
	
}
