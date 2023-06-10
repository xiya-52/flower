package com.example.demo2.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo2.entity.Goods;
import com.example.demo2.entity.Orders;
import com.example.demo2.entity.Shopcar;
import com.example.demo2.entity.User;
import com.example.demo2.mapper.GoodsMapper;
import com.example.demo2.mapper.OrdersMapper;
import com.example.demo2.mapper.ShopcarMapper;
import com.example.demo2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ShopcarController {

    private GoodsMapper goodsMapper;
    private ShopcarMapper shopcarMapper;
    private UserMapper userMapper;
    private OrdersMapper ordersMapper;

    @Autowired
    public ShopcarController(GoodsMapper goodsMapper, ShopcarMapper shopcarMapper, UserMapper userMapper,OrdersMapper ordersMapper) {
        this.goodsMapper = goodsMapper;
        this.shopcarMapper = shopcarMapper;
        this.userMapper = userMapper;
        this.ordersMapper=ordersMapper;
    }


    /**
     * 更新购物车列表
     * @return
     */
    @PostMapping("/addGoodsToShopcar")
    public String addGoodsToShopcar() {
        // 查询goods表中的所有商品
        List<Goods> goodsList = goodsMapper.getAllGoods();

        // 查询logstatu为1的用户
        List<User> userList = userMapper.searchUsersByLogstatu(1);

        int totalCount = goodsList.size();

        for (int i = 0; i < totalCount; i++) {
            Goods goods = goodsList.get(i);
            int goodsId = goods.getId();

            for (User user : userList) {
                int userId = user.getId();

                // 检查是否已存在相同的记录
                if (!shopcarMapper.existsShopcarRecord(goodsId, userId)) {
                    Shopcar shopcar = new Shopcar();
                    shopcar.setGoodsId(goodsId);
                    shopcar.setId(userId);
                    shopcar.setIsJoined(0);
                    shopcar.setNum(0);
                    shopcarMapper.insert(shopcar);
                }
            }
        }

        return "Goods added to shopcar successfully!";
    }

    /**
     * 添加购物列表
     * @param goodsId
     * @return
     */
    @PostMapping("/addToShopcar/{id}")
    public String addToShopcar(@PathVariable("id") int goodsId) {
        // 查询logstatu为1的用户
        List<User> userList = userMapper.searchUsersByLogstatu(1);

        for (User user : userList) {
            int userId = user.getId();

            // 查询购物车中指定用户ID和商品ID的记录
            Shopcar shopcar = shopcarMapper.getShopcarByUserIdAndGoodsId(userId, goodsId);

            if (shopcar != null) {
                int isJoined = shopcar.getIsJoined();

                // 更新isJoined字段的值
                if (isJoined == 0) {
                    shopcar.setIsJoined(1);
                    shopcar.setNum(1);

                } else if (isJoined >= 1) {
                    shopcar.setIsJoined(0);
                    shopcar.setNum(0);
                }

                shopcarMapper.updateShopcar(shopcar);
            }
        }

        return "Successfully updated the shopcar!";
    }

    /**
     * 购物车展示
     * @return
     */
    @GetMapping("/showShopcar")
    public List<Goods> showShopcar() {
        // 寻找logstatu值等于1的用户
        User user = userMapper.getUserByLogstatu(1);
        if (user == null) {
            return new ArrayList<>(); // 如果找不到符合条件的用户，则返回空列表
        }

        int userId = user.getId();

        // 寻找shopcar_id等于user_id并且shopcar_isJoined等于1的数据
        List<Shopcar> shopcarList = shopcarMapper.getShopcarByUserIdAndIsJoined(userId, 1);
        if (shopcarList.isEmpty()) {
            return  new ArrayList<>(); // 如果找不到符合条件的购物车记录，则返回空列表
        }

        // 根据购物车记录中的goodsId列表，查询对应的商品信息
        List<Integer> goodsIdList = new ArrayList<>();
        for (Shopcar shopcar : shopcarList) {
            goodsIdList.add(shopcar.getGoodsId());
        }
        List<Goods> goodsList = goodsMapper.getGoodsByIdList(goodsIdList);
        for (Goods goods : goodsList) {
            for (Shopcar shopcar : shopcarList) {
                if (shopcar.getGoodsId() == goods.getId()) {
                    goods.setIsJoined(1);
                    goods.setNum(shopcar.getNum());
                    break;
                }
            }
        }

        return goodsList;
    }


    /**
     * 购物车增加，减少以及删除
     * @param goodsId
     * @param action
     * @return
     */
    @PostMapping("/updateShopcarItem/{goodsId}/{action}")
    public Shopcar updateShopcarItem  (@PathVariable("goodsId") int goodsId, @PathVariable("action") String action){
        User user = userMapper.getUserByLogstatu(1);
        if (user == null) {
           return null;
        }
        int userId = user.getId();
        Shopcar shopcar = shopcarMapper.getShopcarByUserIdAndGoodsId(userId,goodsId);
        if (shopcar == null) {
            // 如果找不到对应的购物车记录，则返回空
            return null;
        }
        if (action.equals("add")) {
            // 如果操作为 add，将购物车中该商品的数量加1
            int num = shopcar.getNum();
            shopcar.setNum(num + 1);
        } else if (action.equals("dec")) {
            // 如果操作为 dec，将购物车中该商品的数量减1，如果减到0则将isJoined置为0
            int num = shopcar.getNum();
            if (num > 0) {
                shopcar.setNum(num - 1);
            }
            if (shopcar.getNum() == 0) {
                shopcar.setIsJoined(0);
            }
        } else if (action.equals("del")) {
            // 如果操作为 del，将购物车中该商品的数量置为0，并将isJoined置为0
            shopcar.setNum(0);
            shopcar.setIsJoined(0);
        }

        // 更新购物车记录
        shopcarMapper.updateShopcarT(shopcar);

        // 返回更新后的购物车记录
        return shopcar;

    }


    /**
     * 购物车的东西加到订单里面
     * @param totalPrice
     * @param payType
     * @param buyTime
     * @return
     */
    @PostMapping("/shopcarToOrders/{totalPrice}/{payType}/{buyTime}")
    public ResponseEntity<String> shopcarToOrders(
            @PathVariable float totalPrice,
            @PathVariable int payType,
            @PathVariable String buyTime
    ) {
        // 在user表中查找logstatu为1的数据
        User user = userMapper.getEUserByLogstatut(1);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No user found with logstatu = 1");
        }
        int userId = user.getId();
        String userName = user.getName();
        String phone = user.getPhone();
        String address = user.getAddr();
        // 在shopcar表中查找id等于userId且isJoined为1的数据
        List<Shopcar> shopcarList = shopcarMapper.getShopcarByUserIdAndIsJoined(userId,1);


        if (shopcarList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No shopcar data found for user with id = " + userId);
        }


        StringBuilder goodsIdsBuilder = new StringBuilder();
        StringBuilder concatenatedNames=new StringBuilder();

        for (Shopcar shopcar : shopcarList) {
            int goodsId = shopcar.getGoodsId();

            // 通过 goodsId 查询对应的商品记录
            Goods goods = goodsMapper.getGoodById(goodsId);

            // 获取商品的 name 值
            String goodsName = goods.getName();
            goodsIdsBuilder.append(goodsId).append(",");
            concatenatedNames.append(goodsName).append(",");

        }

        String goodsIds = goodsIdsBuilder.deleteCharAt(goodsIdsBuilder.length() - 1).toString();

        String goodsDetails = concatenatedNames.deleteCharAt(concatenatedNames.length() - 1).toString();

        // 在orders表中插入一条新数据
        Orders orders = new Orders();

        orders.setName(userName);
        orders.setPhone(phone);
        orders.setTotalPrice(totalPrice);
        orders.setGoodsId(goodsIds);
        orders.setGoodsDetail(goodsDetails);
        orders.setStatus(2);
        orders.setPaytype(payType);
        orders.setBuyTime(buyTime);
        orders.setAddress(address);
        orders.setUser_id(userId);


        int affectedRows = ordersMapper.insert(orders);

        for (Shopcar shopcar:shopcarList){
            int a=shopcar.getId();
            int b=shopcar.getIsJoined();
            shopcarMapper.updateShopcarsToZero(a,b);
        }

        if (affectedRows > 0) {
            return ResponseEntity.ok("Shopcar to orders conversion successful");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to convert shopcar to orders");
        }

    }

}
