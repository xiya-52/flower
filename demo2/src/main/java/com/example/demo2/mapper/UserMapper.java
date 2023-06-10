package com.example.demo2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

   @Override
   /**
    * 用户新增
    */
   @Options(useGeneratedKeys = true, keyProperty = "id")
   int insert(User user);

   /**
    * 数据库数据调用前台展示
    * @return
    */
   @Select("SELECT id, name, phone, cell, receiver, addr FROM user")
   List<User> getUsers();



   /**
    * 单条数据删除
    * @param orderId
    */
   @Delete("DELETE FROM  Orders WHERE orderId = #{orderId}")
   void deleteOrdersById(@Param("orderId") String orderId);

   /**
    * 数据库数据编辑
    * @param id
    * @return
    */
   @Select("SELECT * FROM user WHERE id = #{id}")
   User getUserById(int id);

   /**
    * 数据库编辑
    * @param user
    * @return
    */
   @Update("UPDATE user SET name = #{name}, phone = #{phone}, cell = #{cell}, receiver = #{receiver}, addr = #{addr} WHERE id = #{id}")
   int updateUser(User user);

   /**
    * 管理员密码修改
    * @param logstatu
    * @return
    */
   @Select("SELECT * FROM user WHERE logstatu = #{logstatu}")
   User getUserByLogstatu(int logstatu);
   @Update("UPDATE user SET password = #{password} WHERE logstatu = #{logstatu}")
   int updateUserPa(User user);


   /**
    * 管理员搜索
    * @param user
    * @return
    */
   @Update("UPDATE user SET logstatu = 0 WHERE logstatu = 1")
   int updateUserSta(User user);

   @Select("SELECT addr,receiver,cell FROM user WHERE logstatu = #{logstatu}")
   User getUserByLogstatut(int logstatu);




   /**
    * 多条数据删除
    * @param ids
    * @return
    */
   @Delete({
           "<script>",
           "DELETE FROM user WHERE id IN",
           "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
           "#{id}",
           "</foreach>",
           "</script>"
   })
   int deleteUsers(@Param("ids") List<Integer> ids);

   /**
    * 用户搜索
    * @param name
    * @return
    */
   @Select("SELECT * FROM user WHERE name = #{name}")
   List<User> searchUsersByName(@Param("name") String name);

   /**
    * 根据登录状态寻找用户
    * @param logstatu
    * @return
    */
   @Select("SELECT * FROM user WHERE logstatu = #{logstatu}")
   List<User> searchUsersByLogstatu(@Param("logstatu") int logstatu);

   @Select("SELECT *FROM user WHERE logstatu = #{logstatu}")
   User getEUserByLogstatut(int logstatu);


}
