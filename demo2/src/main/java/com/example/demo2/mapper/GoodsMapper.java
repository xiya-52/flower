package com.example.demo2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.Category;
import com.example.demo2.entity.Goods;
import com.example.demo2.entity.Shopcar;
import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id")

    int insert(Goods goods);

    @Select("SELECT * FROM goods WHERE name = #{name}")
    Goods getGoodsByName(@Param("name") String name);

    /**
     * 根据id和isJoined值找到特定的值
     * @param categoryName
     * @return
     */
    @Select("SELECT id FROM category WHERE name = #{categoryName}")
    Integer getCategoryIdByName(@Param("categoryName") String categoryName);

    @Update("UPDATE shopcar SET num = 0, isJoined = 0 WHERE id = #{id} AND isJoined = #{isJoined}")
    void updateShopcarsToZero(List<Shopcar> shopcars);
    /**
     * 数据库数据编辑
     * @param id
     * @return
     */
    @Select("SELECT * FROM goods WHERE id = #{id}")
    Goods getGoodById(int id);
    @Update("UPDATE goods SET name = #{name}, image = #{image}, category = #{category}, price = #{price}, category_id= #{category_id} WHERE id = #{id}")
    int updateGood(Goods goods);

    /**
     * 单条数据删除
     * @param id
     */
    @Delete("DELETE FROM  goods WHERE id = #{id}")
    void deleteGoodById(@Param("id") String id);

    /**
     * 多条数据删除
     * @param ids
     * @return
     */
    @Delete({
            "<script>",
            "DELETE FROM goods WHERE id IN",
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    int deleteGoods(@Param("ids") List<Integer> ids);

    /**
     * 类目搜索
     * @param name
     * @return
     */
    @Select("SELECT * FROM goods WHERE name = #{name}")
    List<Goods> searchgoodsByName(@Param("name") String name);


    /**
     * 获取爆款推荐列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 2")
    List<Goods> getNewGoodsRecommendation();

    /**
     * 获取新品推荐列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 1")
    List<Goods> getHotGoodsRecommendation();

    /**
     * 获取现炒荤菜列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 3")
    List<Goods> getNowHGoodsRecommendation();

    /**
     * 获取现炒素菜列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 4")
    List<Goods> getNowSGoodsRecommendation();

    /**
     * 获取营养汤列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 5")
    List<Goods> getSoupGoodsRecommendation();

    /**
     * 获取饮料列表
     * @return
     */
    @Select("SELECT * FROM goods WHERE category_id = 6")
    List<Goods> getDrinkGoodsRecommendation();

    @Select("SELECT * FROM goods")
    List<Goods> getAllGoods();


    @Select({
            "<script>",
            "SELECT * FROM goods WHERE id IN",
            "<foreach item='id' collection='goodsIdList' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "</script>"
    })
    List<Goods> getGoodsByIdList(@Param("goodsIdList") List<Integer> goodsIdList);

    @Select("SELECT * FROM goods where id = #{id}")
    List<Goods>SelectGoodById(@Param("id") int id);

}
