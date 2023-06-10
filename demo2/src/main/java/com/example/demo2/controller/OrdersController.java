package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.DeliveryInfo;
import com.example.demo2.entity.Orders;
import com.example.demo2.entity.User;
import com.example.demo2.mapper.OrdersMapper;
import com.example.demo2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.Timer;
import java.util.TimerTask;

import java.util.*;
import java.time.LocalDateTime;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class OrdersController {

    private OrdersMapper ordersMapper;
    private UserMapper userMapper;

    @Autowired
    public OrdersController(OrdersMapper ordersMapper, UserMapper userMapper) {
        this.ordersMapper = ordersMapper;
        this.userMapper = userMapper;
    }

    /**
     * 订单按照付费等分类展示
     * @param currentPage
     * @param pageSize
     * @param activeTab
     * @return
     */
    @GetMapping("/ordersPage/{currentPage}/{pageSize}/{activeTab}")
    public Page<Orders> getUsersByPageAndActiveTab(
            @PathVariable int currentPage,
            @PathVariable int pageSize,
            @PathVariable int activeTab
    ) {
        Page<Orders> pageRequest = new Page<>(currentPage, pageSize);
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();

        // 根据activeTab设置查询条件
        if (activeTab == 1) {
            queryWrapper.eq("status", 1);
        } else if (activeTab == 2) {
            queryWrapper.eq("status", 2);
        } else if (activeTab == 3) {
            queryWrapper.eq("status", 3);
        } else if (activeTab == 4) {
            queryWrapper.eq("status", 4);
        }

        Page<Orders> ordersPage = ordersMapper.selectPage(pageRequest, queryWrapper);
        List<Orders> ordersList = ordersPage.getRecords();

        for (Orders order : ordersList) {
            DeliveryInfo deliveryInfo = new DeliveryInfo();

            User user = userMapper.selectById(order.getUser_id());
            if (user != null) {
                order.setUser_id(user.getId());
                deliveryInfo.setCell(user.getCell());
                deliveryInfo.setReceiver(user.getReceiver());
                deliveryInfo.setAddress(user.getAddr());
            }

            order.setDeliveryInfo(deliveryInfo.getData());
        }

        return ordersPage;
    }


    /**
     * 发货时间
     * @param id
     * @param currentTime
     * @return
     */
    @PutMapping("/orderStatus/{id}/{currentTime}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable int id, @PathVariable String currentTime) {
        // 根据id和locaTime进行相应操作
        // ...

        // 在这里使用id和locaTime进行后续操作
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(currentTime, formatter);
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MIN);

        int affectedRows = ordersMapper.updateOrderStatusAndBuyTime(id, 3, dateTime);

        if (affectedRows > 0) {
            // 创建一个定时任务，在48小时后自动更新订单状态为4
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int affectedRows = ordersMapper.updateOrderStatus(id, 4);
                    if (affectedRows > 0) {
                        System.out.println("Order status updated to 4 successfully");
                    } else {
                        System.out.println("Failed to update order status to 4");
                    }
                }
            }, TimeUnit.HOURS.toMillis(48));

            return ResponseEntity.ok("Order status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status");
        }
    }


}
