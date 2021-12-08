package com.exam.examcsm.service;

import com.exam.examcsm.dto.ResultDto;
import model.Comment;
import model.Detail;
import model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExamServiceImpl implements ExamService{

    ResultDto resultDto = new ResultDto();

    public ExamServiceImpl() {
        resultDto.setCode(406);
        resultDto.setMsg("fallback");
    }

    @Override
    public List<Detail> getAllDetails() {
        return null;
    }

    @Override
    public Map<String,Object> getUser(String userName, String password) {
        Map<String,Object> result = new HashMap<>();
        result.put("data",null);
        result.put("fb",true);
        return result;
    }

    @Override
    public boolean saveComment(Comment comment) {
        return false;
    }
}
