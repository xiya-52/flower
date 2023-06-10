package com.example.demo2.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.Orders;
import com.example.demo2.entity.User;
import com.example.demo2.mapper.OrdersMapper;
import com.example.demo2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;




@RestController
@CrossOrigin
@RequestMapping("/api")

public class

UserController {
    private UserMapper userMapper;
    private OrdersMapper ordersMapper;



    /**
     *
     * @param userMapper
     */
    @Autowired
    public UserController(OrdersMapper ordersMapper, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.ordersMapper=ordersMapper;
    }


    /**
     * 用户界面查看个人订单
     * @return
     */
    @GetMapping("/getUserOrder")
    public List<Orders> getUserOrder() {
        int logstatu = 1; // logstatu为1
        User user = userMapper.getEUserByLogstatut(logstatu);
        List<Orders> orderList = new ArrayList<>();

        if (user != null) {
            int userId = user.getId();
            QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", userId);
            orderList = ordersMapper.selectList(queryWrapper); // 使用ordersMapper查询所有符合条件的订单

            if (orderList.isEmpty()) {
                // 如果没有符合条件的订单，可以返回null或者抛出异常等处理方式
                return null;
            }
        }

        return orderList;
    }






    /**
     * 用户编辑
     * @param id
     * @param updatedUser
     * @return
     */

    @PutMapping("/userUp/{id}")
    public boolean updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        User user = userMapper.getUserById(id);
        if (user == null) {
            return false; // 返回更新失败的布尔值
        }

        // Update the fields with the new values
        user.setName(updatedUser.getName());
        user.setPhone(updatedUser.getPhone());
        user.setCell(updatedUser.getCell());
        user.setReceiver(updatedUser.getReceiver());
        user.setAddr(updatedUser.getAddr());

        int result = userMapper.updateUser(user);
        return result > 0; // 返回更新是否成功的布尔值
    }

    /**
     * 管理员密码修改
     * @param updatedUser
     * @return
     */
    @PostMapping("/userPassUp")
    public boolean userPasswordUp( @RequestBody User updatedUser) {
        System.out.println(updatedUser.getPassword());

        User user = userMapper.getUserByLogstatu(1);


        if (user == null) {
            return false; // 返回更新失败的布尔值
        }

        user.setPassword(updatedUser.getPassword());

        int result = userMapper.updateUserPa(user);
        return result > 0; // 返回更新是否成功的布尔值
    }


    /**
     * 管理员退出登录
     * @param userQuit
     * @return
     */
    @PostMapping("/userQuit")
    public boolean userQuit(User userQuit) {
        User user = userMapper.getUserByLogstatu(1);

        if (user != null) {
            int result = userMapper.updateUserSta(user);
            return result > 0; // 返回更新是否成功的布尔值
        }

        return false; // 返回更新失败的布尔值
    }


    /**
     * 管理员信息修改
     * @param
     * @param updatedUser
     * @return
     */
    @PutMapping("/editTable")
    public boolean userOgUp(@RequestBody User updatedUser) {
        User user = userMapper.getUserByLogstatu(1);
        if (user == null) {
            return false; // 返回更新失败的布尔值
        }

        user.setReceiver(updatedUser.getReceiver());
        user.setCell(updatedUser.getCell());
        user.setAddr(updatedUser.getAddr());

        int result = userMapper.updateUser(user);
        return result > 0; // 返回更新是否成功的布尔值
    }


    /**
     * 管理员前台展示
     * @return
     */
    @GetMapping("/getTable")
    public List<Map<String, Object>> getAdminInfo() {
        int logstatu = 1; // 管理员的logstatu值为1
        User user = userMapper.getUserByLogstatu(logstatu);

        List<Map<String, Object>> adminInfoList = new ArrayList<>();

        Map<String, Object> adminInfo = new HashMap<>();
        adminInfo.put("addr", user.getAddr());
        adminInfo.put("receiver", user.getReceiver());
        adminInfo.put("cell", user.getCell());

        adminInfoList.add(adminInfo);

        return adminInfoList;
    }



    public static class AdminInfo {
        private String addr;
        private String receiver;
        private String cell;

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getCell() {
            return cell;
        }

        public void setCell(String cell) {
            this.cell = cell;
        }
    }








    /**
     * 用户管理中的所有用户的数据前端展示（分页）
     * @param currentPage 当前页码（从0开始）
     * @param pageSize 每页显示的数据条数
     * @return 分页查询结果
     */
    @GetMapping("/usersPage/{currentPage}/{pageSize}")
    public Page<User> getUsersByPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        Page<User> pageRequest = new Page<>(currentPage, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 设置其他查询条件，如果有的话
        // queryWrapper.eq("xxx", xxx);

        // 执行分页查询
        Page<User> userPage = userMapper.selectPage(pageRequest, queryWrapper);

        System.out.println(userPage.getRecords());

        return userPage;
    }
//        /**
//     * 用户管理中的所有用户的数据前端展示
//     * @return
//     */
//    @GetMapping("/users")
//    public List<User> getUsers() {
//        return userMapper.getUsers();
//    }




    /**
     * 增加用户
     * @param user
     * @return
     */
    @PostMapping("/adduser")
    public boolean createUser(@RequestBody User user) {
        // 查询数据库是否已存在该用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", user.getName());
        User existingUser = userMapper.selectOne(queryWrapper);

        if (existingUser != null) {
            // 数据库已存在该用户
            return false;
        } else {
            // 数据库不存在该用户，执行插入操作
            userMapper.insert(user);
            return true;
        }
    }

    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 删除是否成功
     */
    @DeleteMapping("/delUser/{id}")
    public boolean deleteUser(@PathVariable("id") Long id) {
        int result = userMapper.deleteById(id);
        // 根据删除操作的返回值判断是否删除成功
        return result > 0;
    }

    /**
     * 多个用户删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delUsers")
    public boolean deleteUsers(@RequestBody List<Integer> ids) {
        int result = userMapper.deleteUsers(ids);
        return result > 0;
    }

    /**
     * 用户搜索
     * @param name
     * @return
     */
    @GetMapping("/searchUser/{name}")
    public List<User> searchUsersByName(@PathVariable String name) {
        return userMapper.searchUsersByName(name);
    }



    public static class UserInfo {
        private int id;
        private String name;
        private String phone;
        private String cell;
        private String receiver;
        private String addr;

        // Getter and Setter methods


        public int getId() {
            return id;
        }

        public void setId(int id) {
            id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCell() {
            return cell;
        }

        public void setCell(String cell) {
            this.cell = cell;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
}

