package com.example.demo2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapperLogin extends BaseMapper<User> {
    //登录
    @Select("SELECT COUNT(*) FROM user WHERE name = #{name} AND password = #{password} AND phone = #{phone} AND id = #{id}")
    int checkLoginCredentials(@Param("id") int id,@Param("name") String name, @Param("password") String password, @Param("phone") String phone);

    /**
     * 登录状态修改
     * @param userId
     * @param logStatus
     */
    @Update("UPDATE user SET logstatu = #{logStatus} WHERE id = #{userId}")
    void updateUserLogStatus(@Param("userId") int userId, @Param("logStatus") int logStatus);

    //注册
    @Select("SELECT COUNT(*) FROM user WHERE id = #{id}")
    int countByUsername(@Param("id") int id);
    @Insert("INSERT INTO user (phone, name, password, cell, receiver, addr, isadmin) VALUES (#{phone}, #{name}, #{password}, #{cell}, #{receiver}, #{addr}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);



    @Select("SELECT * FROM user WHERE name = #{username}")
    User getUserByUsername(String username);


}

