package com.lynn.dao;

import com.lynn.pojo.UserInfor;

import java.util.List;

public interface UserInforMapper {

    /**
     * 通过用户id来注销用户
     * @return Result<List>
     */
    List<UserInfor> findAllUser();

    /**
     * 通过用户id来注销用户
     * @param userInfor 用户的对象
     * @return Result<int>
     */
    int newUser(UserInfor userInfor);

    /**
     * 通过用户id来注销用户
     * @param id 用户的id
     * @return Result<int>
     */
    int deleteUserById(int id);

    /**
     * 通过用户id，用户名或者用户电话来查找用户
     * @param userInfor 用户信息对象
     * @return Result<UserInfor>
     */
    UserInfor findUser(UserInfor userInfor);

    /**
     * 传入对象，实现用户信息的更新
     * @param userInfor 用户信息对象
     * @return Result<int>
     */
    int updateUser(UserInfor userInfor);
}
