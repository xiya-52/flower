package com.example.demo2.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo2.entity.Category;
import com.example.demo2.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoryController {


    private CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * 类目管理中的所有用户的数据前端展示（分页）
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/catesPage/{currentPage}/{pageSize}")
    public Page<Category> catesPage(@PathVariable int currentPage, @PathVariable int pageSize) {
        System.out.println(currentPage);
        System.out.println(pageSize);
        Page<Category> pageRequest = new Page<>(currentPage, pageSize);
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        // 设置其他查询条件，如果有的话
        // queryWrapper.eq("xxx", xxx);

        // 执行分页查询
        Page<Category> categoryPage = categoryMapper.selectPage(pageRequest, queryWrapper);

        System.out.println(categoryPage.getRecords());

        return categoryPage;
    }
    /**
     * 增加类型
     * @param category
     * @return
     */
    @PostMapping("/addCategory")
    public boolean createCategory(@RequestBody Category category) {
        // 查询数据库是否已存在该用户
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", category.getName());
        Category existingUser = categoryMapper.selectOne(queryWrapper);

        if (existingUser != null) {
            // 数据库已存在该用户
            return false;
        } else {
            // 数据库不存在该用户，执行插入操作
            categoryMapper.insert(category);
            return true;
        }
    }
    /**
     * 用户编辑
     * @param id
     * @param updateCategory
     * @return
     */

    @PutMapping("/categoryUp/{id}")
    public boolean updateCategory(@PathVariable int id, @RequestBody Category updateCategory) {
        Category category = categoryMapper.getUserById(id);
        if (category == null) {
            return false; // 返回更新失败的布尔值
        }

        // Update the fields with the new values
        category.setId(updateCategory.getId());
        category.setName(updateCategory.getName());

        category.setCategory_id(updateCategory.getId());

        int result = categoryMapper.updateCategory(category);
        return result > 0; // 返回更新是否成功的布尔值
    }

    /**
     * 根据ID删除类目
     * @param id 类目ID
     * @return 删除是否成功
     */
    @DeleteMapping("/delCategory/{id}")
    public boolean delCategory(@PathVariable("id") Long id) {
        int result = categoryMapper.deleteById(id);
        // 根据删除操作的返回值判断是否删除成功
        return result > 0;
    }

    /**
     * 多个类目删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delCategorys")
    public boolean deleteUsers(@RequestBody List<Integer> ids) {
        int result = categoryMapper.deleteCategory(ids);
        return result > 0;
    }

    /**
     * 用户搜索
     * @param name
     * @return
     */
    @GetMapping("/searchCategory/{name}")
    public List<Category> searchCategory(@PathVariable String name) {
        return categoryMapper.searchCategorysByName(name);
    }


}
