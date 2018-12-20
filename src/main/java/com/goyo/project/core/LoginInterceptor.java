/*package com.goyo.project.core;
import java.util.Optional;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.goyo.project.configurer.WebMvcConfigurer;
import com.goyo.project.model.UserInfo;
import com.goyo.project.utils.CommonUtils;
public class LoginInterceptor extends WebMvcConfigurer implements HandlerInterceptor {
 
  @Override
  public void afterCompletion(HttpServletRequest request,
                HttpServletResponse response, Object obj, Exception err)
      throws Exception {
  }
 
  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response,
              Object obj, ModelAndView mav) throws Exception {
 
  }
 
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
               Object obj) throws Exception {
    //如果登录状态不为空则返回true，返回true则会执行相应controller的方法
	  UserInfo currentUserInfo = CommonUtils.getCurrentUserInfo();
	  HttpSession session = request.getSession(false);
	  System.out.println(session);
    if(Optional.ofNullable(currentUserInfo).isPresent()){
      return true;
    }
    //如果登录状态为空则返回信息通知前台，并返回false，不执行原来controller的方法
    response.setHeader("Content-type", "text/html;charset=UTF-8");
    ServletOutputStream outputStream = response.getOutputStream();
    outputStream.write(ResultGenerator.genFailResult(ProjectConstant.PLEASE_LOGIN_FIRST).toString().getBytes("UTF-8"));
    outputStream.flush();
    outputStream.close();
//    response.reset();
    return false;
	  return true;
  }
}*/