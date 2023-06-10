package com.example.demo2.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.Goods;
import com.example.demo2.mapper.GoodsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class Goodscontroller {
    private GoodsMapper goodsMapper;

    @Autowired
    public Goodscontroller(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    /**
     *  菜品（分页）
     * @param  currentPage
     * @param  pageSize
     * @return
     */
    @GetMapping("/goodsPage/{currentPage}/{pageSize}")
    public Page<Goods> goodsPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        Page<Goods> pageRequest = new Page<>(currentPage, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        // 设置其他查询条件，如果有的话
        // queryWrapper.eq("xxx", xxx);

        // 执行分页查询
        Page<Goods> goodsPage = goodsMapper.selectPage(pageRequest, queryWrapper);


        return goodsPage;
    }


    /**
     * 添加菜品
     * @param goodsData 包含商品信息的请求体数据
     * @return 添加是否成功的布尔值
     */
    @PostMapping("/addGoods")
    public boolean addGood(@RequestBody Goods goodsData) {
        // 判断商品是否已存在
        Goods existingGood = goodsMapper.getGoodsByName(goodsData.getName());
        if (existingGood != null) {
            return false; // 商品已存在，返回false
        }



        // 创建新的商品对象
        Goods newGood = new Goods();
        newGood.setName(goodsData.getName());
        newGood.setImage(goodsData.getImage()); // 直接存储Base64格式的图片数据
        newGood.setPrice(goodsData.getPrice());
        newGood.setCategory(goodsData.getCategory());

        // 获取类目ID
        Integer categoryId = goodsMapper.getCategoryIdByName(goodsData.getCategory());
        if (categoryId == null) {
            return false; // 类目不存在，返回false
        }
        newGood.setCategory_id(categoryId);
        newGood.setIsJoined(0); // 默认设置为未加入购物车

        // 执行插入操作
        int result = goodsMapper.insert(newGood);
        return result > 0; // 返回插入是否成功的布尔值
    }

    private boolean isValidBase64(String str) {
        String base64Regex = "^[A-Za-z0-9+/=]+$";
        return str.matches(base64Regex);
    }






    /**
     * 菜品编辑
     * @param  id
     * @param  updateGood
     * @return
     */

    @PutMapping("/goodUp/{id}")
    public boolean updateGood(@PathVariable int id, @RequestBody Goods updateGood) {
        Goods goods = goodsMapper.getGoodById(id);
        if (goods == null) {
            return false; // 返回更新失败的布尔值
        }

        // Update the fields with the new values
        goods.setName(updateGood.getName());
        goods.setImage(updateGood.getImage());
        goods.setPrice(updateGood.getPrice());
        goods.setCategory(updateGood.getCategory());

        // 获取类目ID
        Integer categoryId = goodsMapper.getCategoryIdByName(updateGood.getCategory());
        if (categoryId == null) {
            return false; // 类目不存在，返回false
        }
        goods.setCategory_id(categoryId);
        goods.setIsJoined(0); // 默认设置为未加入购物车

        int result = goodsMapper.updateGood(goods);
        return result > 0; // 返回更新是否成功的布尔值
    }

    /**
     * 根据ID删除菜品
     * @param id 菜品ID
     * @return 删除是否成功
     */
    @DeleteMapping("/delGood/{id}")
    public boolean delGood(@PathVariable("id") Long id) {
        int result = goodsMapper.deleteById(id);
        // 根据删除操作的返回值判断是否删除成功
        return result > 0;
    }

    /**
     * 多个菜品删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delGoods")
    public boolean deleteGoods(@RequestBody List<Integer> ids) {
        int result = goodsMapper.deleteGoods(ids);
        return result > 0;
    }

    /**
     * 菜品搜索
     * @param  name
     * @return 菜品信息
     */
    @GetMapping("/searchGood/{name}")
    public List<Goods> searchCategory(@PathVariable String name) {
        return goodsMapper.searchgoodsByName(name);
    }

    /**
     * 获取新品推荐数据
     * @return 新品推荐商品列表
     */
    @GetMapping("/getNewList")
    public List<Goods> getNewGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getNewGoodsRecommendation();
        return newGoodsList;
    }

    /**
     * 获取爆款推荐数据
     * @return 获取爆款商品列表
     */
    @GetMapping("/getHotList")
    public List<Goods> getHotGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getHotGoodsRecommendation();
        return newGoodsList;
    }

    /**
     * 获取现炒荤菜数据
     * @return 获取现炒荤菜商品列表
     */
    @GetMapping("/getNowHGoodsRecommendation")
    public List<Goods> getNowHGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getNowHGoodsRecommendation();
        return newGoodsList;
    }

    /**
     * 获取现炒荤菜数据
     * @return 获取现炒荤菜商品列表
     */
    @GetMapping("/getNowSGoodsRecommendation")
    public List<Goods> getNowSGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getNowSGoodsRecommendation();
        return newGoodsList;
    }

    /**
     * 获取营养汤数据
     * @return 获取营养汤商品列表
     */
    @GetMapping("/getSoupGoodsRecommendation")
    public List<Goods> getSoupGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getSoupGoodsRecommendation();
        return newGoodsList;
    }

    /**
     * 获取饮品数据
     * @return 获取饮品商品列表
     */
    @GetMapping("/getDrinkGoodsRecommendation")
    public List<Goods> getDrinkGoodsRecommendation() {
        List<Goods> newGoodsList = goodsMapper.getDrinkGoodsRecommendation();
        return newGoodsList;
    }


    /**
     * 获取所有商品信息
     * @return 所有商品列表
     */
    @GetMapping("/allGoods")
    public List<Goods> getAllGoods() {
        List<Goods> allGoodsList = goodsMapper.getAllGoods();
        return allGoodsList;
    }

    /**
     * 商品按照种类展示
     * @param currentPage
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/goodssByCategory/{currentPage}/{pageSize}/{categoryId}")
    public Page<Goods> getGoodsByCategory(@PathVariable int currentPage, @PathVariable int pageSize, @PathVariable int categoryId) {
        Page<Goods> pageRequest = new Page<>(currentPage, pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();

        if (categoryId != 0) {
            queryWrapper.eq("category_id", categoryId);
        }

        // 执行分页查询
        Page<Goods> goodsPage = goodsMapper.selectPage(pageRequest, queryWrapper);

        return goodsPage;
    }


}
