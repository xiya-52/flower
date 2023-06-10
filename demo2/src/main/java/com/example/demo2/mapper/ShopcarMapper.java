    package com.example.demo2.mapper;


    import com.example.demo2.entity.Goods;
    import com.example.demo2.entity.Shopcar;
    import org.apache.ibatis.annotations.*;

    import java.util.List;

    @Mapper
    public interface ShopcarMapper {

        /**
         * 插入用户id以及商品id
         *
         * @param shopcar 购物车信息
         */
        @Insert("INSERT INTO shopcar (goodsId, id, isJoined) VALUES (#{goodsId}, #{id}, #{isJoined})")
        void insert(Shopcar shopcar);

        /**
         * 检查是否已存在相同的记录
         *
         * @param goodsId 商品ID
         * @param id      用户ID
         * @return true表示存在记录，false表示不存在记录
         */
        @Select("SELECT COUNT(*) FROM shopcar WHERE goodsId = #{goodsId} AND id = #{id}")
        boolean existsShopcarRecord(@Param("goodsId") int goodsId, @Param("id") int id);

        /**
         * 根据用户ID和商品ID查询购物车记录
         *
         * @param userId   用户ID
         * @param goodsId  商品ID
         * @return 购物车记录
         */
        @Select("SELECT * FROM shopcar WHERE id = #{userId} AND goodsId = #{goodsId}")
        Shopcar getShopcarByUserIdAndGoodsId(@Param("userId") int userId, @Param("goodsId") int goodsId);

        /**
         * 更新购物车记录的isJoined字段值
         *
         * @param shopcar 购物车记录
         */
        @Update("UPDATE shopcar SET num = #{num}, isJoined = #{isJoined} WHERE id = #{id} AND goodsId = #{goodsId}")
        void updateShopcar(Shopcar shopcar);


        /**
         * 更新两个值
         * @param shopcar
         */
        @Update("UPDATE shopcar SET isJoined = #{isJoined},num=#{num} WHERE id = #{id} AND goodsId = #{goodsId}")
        void updateShopcarT(Shopcar shopcar);


        /**
         * 根据用户ID和isJoined查询购物车记录
         *
         * @param userId    用户ID
         * @param isJoined  是否加入购物车的状态，1表示已加入
         * @return 符合条件的购物车记录列表
         */
        @Select("SELECT * FROM shopcar WHERE id = #{id} AND isJoined = #{isJoined}")
        List<Shopcar> getShopcarByUserIdAndIsJoined(@Param("id") int userId, @Param("isJoined") int isJoined);


        /**
         * 根据id搜索内容
         * @param id
         * @return
         */
        @Select("SELECT * FROM shopcar where id = #{id}")
        List<Goods>SelectShopById(@Param("id") int id);


        @Select("SELECT * FROM shopcar WHERE isJoined = #{isJoined} AND id = #{id}")
        List<Shopcar> getShopcarsByIsJoinedAndId(@Param("isJoined") int isJoined, @Param("id") int id);


        /**
         * 根据商品ID列表查询商品名称列表
         *
         * @param goodsIds 商品ID列表
         * @return 商品名称列表
         */
        @Select("<script>" +
                "SELECT name FROM goods WHERE id IN " +
                "<foreach item='goodsId' collection='goodsIds' open='(' separator=',' close=')'>" +
                "#{goodsId}" +
                "</foreach>" +
                "</script>")
        List<String> getGoodsNamesByIds(@Param("goodsIds") List<Integer> goodsIds);



        @Update("UPDATE shopcar SET num = 0, isJoined = 0 WHERE id = #{id} AND isJoined = #{isJoined}")
        void updateShopcarsToZero(@Param("id") int isJoined, @Param("isJoined") int id);
    }
