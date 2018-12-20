package com.goyo.project.jiwei.web;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.CommonDataForPetitionReport;
import com.goyo.project.core.Result;
import com.goyo.project.model.PetitionReport;

/**
 * @author: JinYue
 * @ClassName: PetitionReportController 
 * @Description: 信访举报(监督举报)
 */
@RestController
@RequestMapping("/petitionReport")
public class PetitionReportController extends CommonDataForPetitionReport {
	
	
	//*********************************************信访举报CRUD************************************************
	 /**
     * 信访举报查询(single)
     * @return Result<PetitionReport>
     */
	@PostMapping("/petitionReportSingle")
    public Result<PetitionReport> petitionReportSingle(PetitionReport petitionReport){
		return getPetitionReportSingle(petitionReport);
    }
	
    /**
     * 信访举报查询(all)
     * @return Result<Page<PetitionReport>>
     */
	@PostMapping("/petitionReportPage")
    public Result<Page<PetitionReport>> petitionReport(PetitionReport petitionReport,String pageNum,String pageSize
			){
		return getPetitionReportPage(petitionReport, pageNum, pageSize);
    }
	 /**
     * 信访举报查询(all:更多)
     * @return Result<Page<PetitionReport>>
     */
	@PostMapping("/petitionReportPageMore")
    public Result<Page<PetitionReport>> petitionReportMore(PetitionReport petitionReport,String pageNum,String pageSize,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date startTime,
    		@DateTimeFormat(pattern = "yyyy-MM-dd")Date endTime){
		return getPetitionReportPage(petitionReport, pageNum, pageSize,startTime,endTime);
    }
    /**
     * 信访举报添加;
     * @return Result<String>
     */
	@PostMapping("/petitionReportAdd")
    public Result<String> petitionReportAdd(PetitionReport petitionReport){
		return getPetitionReportAdd(petitionReport);
       
    }
	
	 /**
     * 信访举报更新;
     * @return Result<String>
     */
	@PostMapping("/petitionReportUpdate")
    public Result<String> petitionReportUpdate(PetitionReport petitionReport){
		return getPetitionReportUpdate(petitionReport);
       
    }

    /**
     * 信访举报删除;
     * @return Result<String>
     */
	@PostMapping("/petitionReportDel")
    public Result<String> petitionReportDel(PetitionReport petitionReport){
		return getPetitionReportDel(petitionReport);
    }
	
}
