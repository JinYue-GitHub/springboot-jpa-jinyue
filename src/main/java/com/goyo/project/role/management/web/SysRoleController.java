package com.goyo.project.role.management.web;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.core.Result;
import com.goyo.project.core.ResultGenerator;
import com.goyo.project.model.SysRole;
import com.goyo.project.role.management.dao.SysRoleDao;

@RestController
@RequestMapping("/roleInfo")
public class SysRoleController {
	@Resource
	private SysRoleDao sysRoleDao;
	
    /**
     * 角色查询
     * @return
     */
	@PostMapping("/roleList")
//    @RequiresPermissions("role:view")//角色管理;
    public Result<SysRole> roleInfo(SysRole sysRole){
        return ResultGenerator.genSuccessResult(sysRoleDao.findById(sysRole.getId()).get());
    }

    /**
     * 角色添加;
     * @return
     */
	@PostMapping("/roleAdd")
//    @RequiresPermissions("role:add")//角色管理;
    public Result<String> roleAdd(SysRole sysRole){
		sysRoleDao.save(sysRole);
        return ResultGenerator.genSuccessResult();
        
    }
	
	/**
     * 角色更新;
     * @return
     */
	@PostMapping("/roleUpdate")
//    @RequiresPermissions("role:update")//角色管理;
    public Result<String> roleUpdate(SysRole sysRole){
		sysRoleDao.save(sysRole);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 角色删除;
     * @return
     */
	@PostMapping("/roleDel")
//    @RequiresPermissions("role:del")//角色管理;
    public Result<String> roleDel(SysRole sysRole){
		sysRoleDao.deleteById(sysRole.getId());
        return ResultGenerator.genSuccessResult();
    }
}