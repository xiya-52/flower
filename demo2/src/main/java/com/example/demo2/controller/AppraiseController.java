package com.example.demo2.controller;


import com.example.demo2.entity.Appraise;
import com.example.demo2.mapper.AppraiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AppraiseController {
    private AppraiseMapper appraiseMapper;

    @Autowired
    public AppraiseController(AppraiseMapper appraiseMapper) {
        this.appraiseMapper = appraiseMapper;
    }

    @PostMapping("/insertappraises/{well}/{content}")
    public void addAppraise(@PathVariable("well") String well, @PathVariable("content") String content) {
        Appraise appraise = new Appraise();
        appraise.setWell(well);
        appraise.setContent(content);
        appraiseMapper.insert(appraise);
    }

    @GetMapping("/outappraises")
    public List<Appraise> getAllAppraises() {
        return appraiseMapper.getAllAppraises();
    }

}
