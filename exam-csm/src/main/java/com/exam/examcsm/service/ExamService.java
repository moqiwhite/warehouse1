package com.exam.examcsm.service;

import model.Comment;
import model.Detail;
import model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "exam-pdr",fallback = ExamServiceImpl.class)
public interface ExamService {

    @GetMapping("/getAllDetails")
    List<Detail> getAllDetails();

    @GetMapping("/getUser")
    Map<String,Object> getUser(@RequestParam("userName") String userName, @RequestParam("password") String password);

    @PostMapping("/saveComment")
    boolean saveComment(@RequestBody Comment comment);

}
