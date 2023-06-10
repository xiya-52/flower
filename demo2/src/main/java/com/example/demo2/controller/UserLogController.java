package com.example.demo2.controller;

import com.example.demo2.mapper.UserMapper;
import com.example.demo2.mapper.UserMapperLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo2.entity.User;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserLogController {
    private UserMapperLogin userMapperLogin;

    @Autowired
    public UserLogController(UserMapperLogin userMapperLogin) {
        this.userMapperLogin = userMapperLogin;
    }

    /**
     * 账号登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public int loginUser(@RequestBody User user) {
        System.out.println("user + " + user);
        int count = 0;
        // 验证账号和密码是否匹配
        count = userMapperLogin.checkLoginCredentials(user.getId(), user.getName(), user.getPassword(), user.getPhone());
        if (count > 0) {
            User loggedInUser = userMapperLogin.getUserByUsername(user.getName());
            if (loggedInUser.getIsAdmin() == 1) {
                userMapperLogin.updateUserLogStatus(loggedInUser.getId(), 1);
                return 1;
            } else {
                // 登录成功，更新logstatu字段的值为1
                userMapperLogin.updateUserLogStatus(loggedInUser.getId(), 1);
                return 2;
            }
        } else {
            return 0;
        }
    }


    /**
     * 账号退出
     * @param user
     * @return
     */
    @PostMapping("/register")
    public boolean registerUser(@RequestBody User user) {
        int countByUsername = userMapperLogin.countByUsername(user.getId());
        if (countByUsername > 0) {
            // 用户名已存在，注册失败
            return false;
        } else {
            // 设置默认值
            user.setIsAdmin(0);

            // 执行插入操作
            userMapperLogin.insertUser(user);

            return true;
        }
    }
}

