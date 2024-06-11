package com.yky.mishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yky.mishop.model.entity.User;

/**
* @author yky
* @description 针对表【user(用户表 )】的数据库操作Service
* @createDate 2024-06-06 22:45:21
*/
public interface UserService extends IService<User> {

    /**
     * 判断用户名是否存在
     *
     * @param username  用户名
     * @return 用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 用户注册
     *
     * @param username  用户名
     * @param password  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    public long userRegister(String username, String password, String checkPassword);
}
