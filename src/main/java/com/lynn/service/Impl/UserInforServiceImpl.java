package com.lynn.service.Impl;

/**
 * 〈由注解标记的一个service类〉<br>
 * 〈〉
 *
 * @author 景景
 * @create 2019/12/29
 * @since 1.0.0
 */

import com.lynn.dao.UserInforMapper;
import com.lynn.pojo.UserInfor;
import com.lynn.service.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInforServiceImpl implements UserInforService {
    /**
     *创建数据库操作接口对象
     */
    @Autowired
    UserInforMapper userInforMapper;

    /**
     * 登录验证，用户密码是否正确
     */
    @Override
    public Boolean loginCheck(String phone, String password) {
        UserInfor userInfor = new UserInfor();
        userInfor.setInfor_phone(phone);
        UserInfor u = userInforMapper.findUser(userInfor);
        // 判断输入的用户是否存在，密码是否正确
        if(u!=null) {
            if(u.getInfor_password().equals(password)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 手机注册
     */
    @Override
    public Boolean registerByPhone(String phone, String password) {
        // 将未知信息用手机号拼接直接实现注册，后续用户再修改信息
        UserInfor userInfor = new UserInfor();
        userInfor.setInfor_name(phone);
        userInfor.setInfor_phone(phone);
        userInfor.setInfor_password(password);
        userInforMapper.newUser(userInfor);
        return true;
    }

    /**
     * 手机验证（是否已经注册）
     */
    @Override
    public Boolean phoneCheck(String phone) {
        UserInfor userInfor = new UserInfor();
        userInfor.setInfor_phone(phone);
        if(userInforMapper.findUser(userInfor)!=null) {
            return false;
        }
        return true;
    }



    /**
     * 通过用户名来获取用户所有信息
     */
    @Override
    public UserInfor findUserByName(String name) {
        System.out.println("按用户名查询用户执行！");
        UserInfor userInfor = new UserInfor();
        userInfor.setInfor_name(name);
        return userInforMapper.findUser(userInfor);
    }

    /**
     * 通过用户手机来获取用户所有信息
     */
    @Override
    public UserInfor findUserByPhone(String phone) {
        System.out.println("按用户名查询用户执行！");
        UserInfor userInfor = new UserInfor();
        userInfor.setInfor_phone(phone);
        return userInforMapper.findUser(userInfor);
    }

    /**
     * 查找所有的用户信息
     */
    @Override
    public List<UserInfor> findAllUser() {
        System.out.println("查询所有执行！");
        List<UserInfor> u = userInforMapper.findAllUser();
        return u;
    }

    /**
     * 修改用户信息
     */
    @Override
    public Boolean updateUserInfor(String userId, String userName, String userPhone, String userPassword,
                                   String userPortrait, String userAutograph, int userSex, int userState) {
        int id = Integer.parseInt(userId);
        UserInfor userInfor = new UserInfor();
        userInfor.setUser_id(id);
        userInfor.setInfor_name(userName);
        userInfor.setInfor_phone(userPhone);
        userInfor.setInfor_password(userPassword);
        userInfor.setInfor_portrait(userPortrait);
        userInfor.setInfor_autograph(userAutograph);
        userInfor.setInfor_sex(userSex);
        int isSuccess = userInforMapper.updateUser(userInfor);
        System.out.println("修改用户信息执行！"+isSuccess);
        // 更新语句成功
        if(isSuccess == 1) {
            return true;
        }else {
            return false;
        }

    }

}