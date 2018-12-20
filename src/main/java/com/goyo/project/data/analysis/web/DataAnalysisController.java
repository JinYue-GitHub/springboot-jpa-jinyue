package com.goyo.project.data.analysis.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.ProjectConstant;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Organization;
import com.goyo.project.model.UserInfo;
import com.goyo.project.organization.management.dao.UserInfoDao;
/**
 * @author: JinYue
 * @ClassName: DataAnalysisController 
 * @Description: 数据分析
 */
@RestController
@RequestMapping("/dataAnalysis")
public class DataAnalysisController {
	
	@Resource
	private UserInfoDao userInfoDao;
	
	/**
     * 积极分子年龄查询(single)
     * @return Result<UserInfo>
     */
	@PostMapping("/dataAnalysisForActivistsAge")
    public Result<List<Integer>> dataAnalysisForActivistsAge(UserInfo userInfo,Organization organization){
		
		List<Integer> list = new ArrayList<>();
		Stream<UserInfo> stream = userInfoDao
		.findByPoliticalAndOrganizationIds(ProjectConstant.ACTIVISTS, organization.getIds())
		.stream();
		int sum25 		= 	0;
		int sum25_30 	=   0;
		int sum30_35	=   0;
		int sum35_40 	=   0;
		int sum40_45	=   0;
		int sum45_50	=   0;
		int sum50_55 	=   0;
		int sum55 		=   0;
		if(Optional.ofNullable(stream).isPresent()){
			sum25 		= 	stream.filter(u -> u.getAge()<=ProjectConstant.TWENTY_FIVE).mapToInt(u -> 1).sum();
			sum25_30 	= 	stream.filter(u -> u.getAge()>ProjectConstant.TWENTY_FIVE&&u.getAge()<=ProjectConstant.THIRTY).mapToInt(u -> 1).sum();
			sum30_35	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY&&u.getAge()<=ProjectConstant.THIRTY_FIVE).mapToInt(u -> 1).sum();
			sum35_40 	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY_FIVE&&u.getAge()<=ProjectConstant.FORTY).mapToInt(u -> 1).sum();
			sum40_45	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY&&u.getAge()<=ProjectConstant.FORTY_FIVE).mapToInt(u -> 1).sum();
			sum45_50	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY_FIVE&&u.getAge()<=ProjectConstant.FIFTY).mapToInt(u -> 1).sum();
			sum50_55 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY&&u.getAge()<=ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
			sum55 		= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
		}
		
		list.add(sum25);
		list.add(sum25_30);
		list.add(sum30_35);
		list.add(sum35_40);
		
		list.add(sum40_45);
		list.add(sum45_50);
		list.add(sum50_55);
		list.add(sum55);
        return ResultGenerator.genSuccessResult(list);
    }
	
	
	/**
     * 预备党员年龄数据分析
     * @return Result<UserInfo>
     */
	@PostMapping("/dataAnalysisForProbationaryPartyMember")
    public Result<List<Integer>> dataAnalysisForProbationaryPartyMember(UserInfo userInfo,Organization organization){
		
		List<Integer> list = new ArrayList<>();
		Stream<UserInfo> stream = userInfoDao
		.findByPoliticalAndOrganizationIds(ProjectConstant.PROBATIONARY_PARTY_MEMBER, organization.getIds())
		.stream();
		int sum25 		= 	0;
		int sum25_30 	=   0;
		int sum30_35 	=   0;
		int sum35_40 	=   0;
		int sum40_45 	=   0;
		int sum45_50 	=   0;
		int sum50_55 	=   0;
		int sum55 		=   0;
		if(Optional.ofNullable(stream).isPresent()){
			sum25 		= 	stream.filter(u -> u.getAge()<=ProjectConstant.TWENTY_FIVE).mapToInt(u -> 1).sum();
			sum25_30 	= 	stream.filter(u -> u.getAge()>ProjectConstant.TWENTY_FIVE&&u.getAge()<=ProjectConstant.THIRTY).mapToInt(u -> 1).sum();
			sum30_35 	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY&&u.getAge()<=ProjectConstant.THIRTY_FIVE).mapToInt(u -> 1).sum();
			sum35_40 	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY_FIVE&&u.getAge()<=ProjectConstant.FORTY).mapToInt(u -> 1).sum();
			sum40_45 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY&&u.getAge()<=ProjectConstant.FORTY_FIVE).mapToInt(u -> 1).sum();
			sum45_50 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY_FIVE&&u.getAge()<=ProjectConstant.FIFTY).mapToInt(u -> 1).sum();
			sum50_55 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY&&u.getAge()<=ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
			sum55 		= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
		}
		
		list.add(sum25);
		list.add(sum25_30);
		list.add(sum30_35);
		list.add(sum35_40);
		
		list.add(sum40_45);
		list.add(sum45_50);
		list.add(sum50_55);
		list.add(sum55);
        return ResultGenerator.genSuccessResult(list);
    }
	
	
	/**
     * 党员发展历史数量(这部分数据来源不明确，从哪里来？)
     * @return Result<List<Integer>>
     */
	@PostMapping("/dataAnalysisForPartyMemberHistory")
//    @RequiresPermissions("userInfo:view")//权限管理;
    public Result<List<Integer>> dataAnalysisForPartyMemberHistory(UserInfo userInfo,Organization organization){
		
		List<Integer> list = new ArrayList<>();
		Stream<UserInfo> stream = userInfoDao
		.findByPoliticalAndOrganizationIds(ProjectConstant.PROBATIONARY_PARTY_MEMBER, organization.getIds())
		.stream();
		int sum25 		=	0;
		int sum25_30 	=	0;
		int sum30_35 	=   0;
		int sum35_40 	=   0;
		int sum40_45 	=   0;
		int sum45_50 	=   0;
		int sum50_55 	=   0;
		int sum55 		=   0;
		if(Optional.ofNullable(stream).isPresent()){
			sum25 		= 	stream.filter(u -> u.getAge()<=ProjectConstant.TWENTY_FIVE).mapToInt(u -> 1).sum();
			sum25_30 	=	stream.filter(u -> u.getAge()>ProjectConstant.TWENTY_FIVE&&u.getAge()<=ProjectConstant.THIRTY).mapToInt(u -> 1).sum();
			sum30_35 	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY&&u.getAge()<=ProjectConstant.THIRTY_FIVE).mapToInt(u -> 1).sum();
			sum35_40 	= 	stream.filter(u -> u.getAge()>ProjectConstant.THIRTY_FIVE&&u.getAge()<=ProjectConstant.FORTY).mapToInt(u -> 1).sum();
			sum40_45 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY&&u.getAge()<=ProjectConstant.FORTY_FIVE).mapToInt(u -> 1).sum();
			sum45_50 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FORTY_FIVE&&u.getAge()<=ProjectConstant.FIFTY).mapToInt(u -> 1).sum();
			sum50_55 	= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY&&u.getAge()<=ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
			sum55 		= 	stream.filter(u -> u.getAge()>ProjectConstant.FIFTY_FIVE).mapToInt(u -> 1).sum();
		}
		
		list.add(sum25);
		list.add(sum25_30);
		list.add(sum30_35);
		list.add(sum35_40);
		
		list.add(sum40_45);
		list.add(sum45_50);
		list.add(sum50_55);
		list.add(sum55);
        return ResultGenerator.genSuccessResult(list);
    }
}