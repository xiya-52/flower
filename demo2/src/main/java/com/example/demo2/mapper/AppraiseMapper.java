package com.example.demo2.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo2.entity.Appraise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppraiseMapper extends BaseMapper<Appraise> {
    default int insertAppraise(Appraise appraise) {
        return insert(appraise);
    }

    default List<Appraise> getAllAppraises() {
        return selectList(null);
    }
}
