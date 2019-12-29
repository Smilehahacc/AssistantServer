package com.lynn.controller;

import com.lynn.pojo.UserInfor;
import com.lynn.service.UserInforService;
import com.lynn.util.ZhenziSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class UserInforController {
    /**
     *创建数据库操作接口对象
     */
    @Autowired
    UserInforService userInforService;

    /**
     *用户登录
     */
    @RequestMapping(value ="/login")
    public String login(@RequestParam("phone") String phone, @RequestParam("password") String password,
                        HttpServletResponse response) {
        // 判断验证结果
        if(userInforService.loginCheck(phone,password)) {
            Cookie cookie=new Cookie("userId",userInforService.findUserByPhone(phone).getUser_id()+"");
            response.addCookie(cookie);
            Cookie cookie1=new Cookie("userName",userInforService.findUserByPhone(phone).getInfor_phone());
            response.addCookie(cookie1);
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *接收RequestBody为Json串时的登录请求
     */
    @RequestMapping(value ="/loginJson")
    public String loginJson(@RequestBody Map requestBody){

        // 判断登录验证结果
        if(userInforService.loginCheck((String)requestBody.get("name"),(String)requestBody.get("password"))) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    /**
     *用户注册（手机验证注册）
     */
    @RequestMapping(value ="/registerByPhone")
    public String registerByPhone(@RequestParam("phone") String phone, @RequestParam("password") String password,
                                  @RequestParam("code") String code, HttpServletRequest request) {

        String codeNow = "";
        // 判断注册验证结果
        if(userInforService.phoneCheck(phone)) {
            // 检查cookie是否与输入的验证码相同
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(phone)) {
                        codeNow = cookie.getValue();
                    }
                }
            }
            // 验证码正确，成功注册
            if(codeNow.equals(code)) {
                userInforService.registerByPhone(phone,password);
                return "SUCCESS";
            }
            return "ERROR";
        } else {
            return "ERROR";
        }
    }

    /**
     *发送短信
     */
    @RequestMapping(value ="/sendSms")
    public String sendSms(@RequestParam("phone") String phone, HttpServletResponse response) throws Exception {

        // 设置验证码有效时间
        int min =5;
        // 手机未被注册，则调用接口发送短信，并创建生命期为min分钟的cookie
        if(userInforService.phoneCheck(phone)) {
            String code = ZhenziSms.getInstance().sendSMS(phone);
            Cookie cookie=new Cookie(phone,code);
            cookie.setMaxAge(60*min);
            response.addCookie(cookie);
            return "SUCCESS";
        }
        return "ERROR";
    }

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByName")
    public UserInfor findUserByName(@RequestParam("name") String name
    ){
        return userInforService.findUserByName(name);
    }

    /**
     *通过用户名来获取用户所有信息
     */
    @RequestMapping(value ="/findUserByPhone")
    public UserInfor findUserByPhone(@RequestParam("phone") String phone
    ){
        return userInforService.findUserByPhone(phone);
    }

    /**
     *查找所有用户
     */
    @RequestMapping(value ="/findAll")
    public List<UserInfor> findAll(){
        return  userInforService.findAllUser();
    }

    /**
     *获取用户Id
     */
    @RequestMapping(value ="/getUserIdByCookie")
    public String getUserIdByCookie(HttpServletRequest request) {
        String userId = "1";
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userId")) {
                    userId =  cookie.getValue();
                }
            }
        }
        return userId;
    }

    /**
     *获取用户Id
     */
    @RequestMapping(value ="/updateUserInfor")
    public String updateUserInfor(@RequestParam("userId") String userId, @RequestParam("userName") String userName,
                                  @RequestParam("userPhone") String userPhone, @RequestParam("userPassword") String userPassword,
                                  @RequestParam("userPortrait") String userPortrait, @RequestParam("userAutograph") String userAutograph,
                                  @RequestParam("userPortrait") int userSex, @RequestParam("userAutograph") int userState,
                                  HttpServletRequest request, HttpServletResponse response) {
        Boolean isSuccess = userInforService.updateUserInfor(userId, userName, userPhone, userPassword, userPortrait, userAutograph, userSex, userState);
        String name ="";
        int min = 60*60*24*7;
        // 成功用户名则更新cookie
        if(isSuccess) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("userName")) {
                        name =  cookie.getValue();
                    }
                }
            }
            // 名字已被修改，更新cookie
            if(!userName.equals(name)) {
                Cookie cookie=new Cookie(userName,name);
                cookie.setMaxAge(60*min);
                response.addCookie(cookie);
            }
        }
        if(isSuccess) {
            return "SUCCESS";
        }else {
            return "ERROR";
        }
    }

}