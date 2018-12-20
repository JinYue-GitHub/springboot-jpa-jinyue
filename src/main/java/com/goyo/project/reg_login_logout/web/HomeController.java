package com.goyo.project.reg_login_logout.web;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.goyo.project.common.DataForHomeController;
import com.goyo.project.core.Result;
import com.goyo.project.model.UserInfo;
import java.util.Map;
/**
 * @author: JinYue
 * @ClassName: HomeController 
 * @Description: 注册登录
 */
@CrossOrigin
@RestController
//@CrossOrigin(origins = "http://10.5.95.60:9095", maxAge = 3600)
public class HomeController extends DataForHomeController {
	//主页跳转
	@PostMapping({"/","/index"})
    public Result<String> index(){
        return getIndex();
    }
	//注册
	@PostMapping("/register")//@Valid 
    public Result<Object> register(UserInfo userInFo,BindingResult bindingResult, Map<String, Object> map) throws Exception{
		return getRegister(userInFo, bindingResult);
    }
	//登录
    @PostMapping("/login")
    public Result<Object> login(UserInfo userInfo) throws Exception{
    	return getLogin(userInfo);
    }
    //注销
    @PostMapping("/logout")
    public Result<String> logout() throws Exception{
    	return getLogout();
    }
    //无权限
    @PostMapping("/403")
    public Result<String> unauthorizedRole(){
        return getUnauthorizedRole();
    }
}