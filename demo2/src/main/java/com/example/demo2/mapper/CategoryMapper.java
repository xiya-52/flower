package com.example.demo2.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.Category;
import com.example.demo2.entity.Goods;
import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    /**
     * 数据库数据编辑
     * @param id
     * @return
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getUserById(int id);
    @Update("UPDATE category SET name = #{name} WHERE id = #{id}")
    int updateCategory(Category category);

    /**
     * 单条数据删除
     * @param orderId
     */
    @Delete("DELETE FROM  Orders WHERE orderId = #{orderId}")
    void deleteOrdersById(@Param("orderId") String orderId);

    /**
     * 多条数据删除
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
            "DELETE FROM category WHERE id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteCategory(@Param("ids") List<Integer> ids);

    /**
     * 类目搜索
     * @param name
     * @return
     */
    @Select("SELECT * FROM category WHERE name = #{name}")
    List<Category > searchCategorysByName(@Param("name") String name);




}
