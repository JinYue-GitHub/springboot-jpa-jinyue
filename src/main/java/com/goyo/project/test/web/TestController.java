/*package com.goyo.project.test.web;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.BaseRepository;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.Test;
import com.goyo.project.test.dao.TestDao;
*//**
 * @author: JinYue
 * @ClassName: TestController 
 * @Description: 测试类
 *//*
@RestController
public class TestController {
	
	@Resource
	private BaseRepository<Test, String> baserepository;
	
	@Resource
	private TestDao testDao;
	
	//*********************************************测试CRUD************************************************
	 *//**
     * 测试查询(single)
     * @return Result<Test>
     *//*
	@PostMapping("/testSingle")
    public Result<Test> testSingle(Test test){
		Test findById = baserepository.findById(test, test.getId());
		System.out.println(findById);
		return ResultGenerator.genSuccessResult(findById);
    }
    *//**
     * 测试查询(all)
     * @return Result<Page<Test>>
     *//*
	@PostMapping("/testPage")
    public Result<Page<Test>> test(Test test,String pageNum,String pageSize,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date startTime,
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")Date endTime){
		Page<Test> page = null;
//		Pageable pageable = null;
		return ResultGenerator.genSuccessResult(page);
    }
    *//**
     * 测试添加;
     * @return Result<String>
     *//*
	@PostMapping("/testAdd")
    public Result<String> testAdd(Test test){
//		baserepository.save(test);
		testDao.save(test);
		return ResultGenerator.genSuccessResult();
       
    }
	
	 *//**
     * 测试更新;
     * @return Result<String>
     *//*
	@PostMapping("/testUpdate")
    public Result<String> testUpdate(Test test){
//		baserepository.update(test);
		testDao.save(test);
		return ResultGenerator.genSuccessResult();
       
    }

    *//**
     * 测试删除;
     * @return Result<String>
     *//*
	@PostMapping("/testDel")
    public Result<String> testDel(Test test){
		baserepository.delete(test);
		return ResultGenerator.genSuccessResult();
    }
	
}
*/