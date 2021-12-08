package com.exam.exampdr.service;

import com.alibaba.fastjson.JSON;
import com.exam.exampdr.dao.ExamDao;
import model.Comment;
import model.Detail;
import model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExamService {

    @Resource
    private ExamDao dao;

    @GetMapping("/getAllDetails")
    List<Detail> getAllDetails(){
        System.out.println("2222");
        return dao.getAll();
    }

    @GetMapping("/getUser")
    Map<String,Object> getUser(String userName, String password){

        Map<String,Object> result = new HashMap<>();
        result.put("data",JSON.toJSONString(dao.getUser(userName, password)));
        result.put("fb",false);
        return result;

    }

    @PostMapping("/saveComment")
    boolean saveComment(@RequestBody Comment comment){
        int i = dao.addComment(comment);
        if (i>0){
            return true;
        }
        return false;
    }


}
