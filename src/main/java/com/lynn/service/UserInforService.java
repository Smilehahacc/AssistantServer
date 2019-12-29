package com.lynn.service;

/**
 * 〈实现service类的接口〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2019/12/29
 * @since 1.0.0
 */

import com.lynn.pojo.UserInfor;

import java.util.List;

public interface UserInforService {

    Boolean loginCheck(String phone, String password);
    Boolean registerByPhone(String phone, String password);
    Boolean phoneCheck(String phone);
    UserInfor findUserByName(String name);
    UserInfor findUserByPhone(String phone);
    List<UserInfor> findAllUser();
    Boolean updateUserInfor(String userId, String userName, String userPhone, String userPassword, String userPortrait, String userAutograph, int userSex, int userState);
}