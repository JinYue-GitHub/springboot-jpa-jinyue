/*package com.goyo.project.web;
import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

*//**
* Created by JinYue on 2018-06-19.
*//*
@RestController
@RequestMapping("/test/table")
public class TestTableController {
    @Resource
    private TestTableService testTableService;

    @PostMapping("/add")
    public Result add(TestTable testTable) {
    	testTable.setUuid(UUID.randomUUID().toString().replace("-", ""));
    	testTable.setTimeTemp(new Timestamp((new Date()).getTime()));
        testTableService.save(testTable);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        testTableService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(TestTable testTable) {
        testTableService.update(testTable);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        TestTable testTable = testTableService.findById(id);
        return ResultGenerator.genSuccessResult(testTable);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TestTable> list = testTableService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
*/