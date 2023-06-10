package com.example.demo2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.Orders;
import com.example.demo2.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
    /**
     * 订单状态
     * @param id
     * @param status
     * @return
     */
    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateOrderStatus(@Param("id") int id, @Param("status") int status);


    /**
     * 查询不同状态的订单
     * @param status
     * @return
     */
    @Select("SELECT * FROM orders WHERE status = #{status}")
    List<Orders> searchOrdersByStatus(@Param("status") int status);

    /**
     * 定时发货
     * @param id
     * @param status
     * @param buyTime
     * @return
     */
    @Update("UPDATE orders SET status = #{status}, buyTime = #{buyTime} WHERE id = #{id}")
    int updateOrderStatusAndBuyTime(@Param("id") int id, @Param("status") int status, @Param("buyTime") LocalDateTime buyTime);


}
