package com.exam.examcsm.controller;

import com.alibaba.fastjson.JSON;
import com.exam.examcsm.config.MD5;
import com.exam.examcsm.config.RedisUtils;
import com.exam.examcsm.dto.ResultDto;
import com.exam.examcsm.service.ExamService;
import model.Comment;
import model.Detail;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class ExamController {

    @Resource
    private ExamService service;
    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/queryDetails")
    public ResultDto queryDetails(){
        List<Detail> details = service.getAllDetails();

        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMsg("success");
        resultDto.setData(details);

//        String jsonDetails = JSON.toJSONString(details);
//        System.out.println(jsonDetails);
//        resultDto.setData(jsonDetails);

        return resultDto;
    }

    @RequestMapping("/login")
    public ResultDto login(String userName,String password){
        ResultDto resultDto = new ResultDto();
        //判断
        Map<String,Object> result = service.getUser(userName,MD5.md5(password));
        if ((boolean)result.get("fb")){
            resultDto.setCode(406);
            resultDto.setMsg("fallback");
            return resultDto;
        }

        User user = JSON.parseObject((String)result.get("data"),User.class);
        if (null==user){
            resultDto.setCode(403);
            resultDto.setMsg("wrong password");
        }else {
            //存token
            String token = getToken(user);
            saveRedis(user,token);
            //返回
            resultDto.setCode(200);
            resultDto.setMsg("success");
            resultDto.setData(token);
        }
        return resultDto;
    }

    @RequestMapping("/addComment")
    public ResultDto addComment(int blogId, String content, HttpServletRequest request){
        String token = request.getHeader("token");
        String jsonUser = (String) redisUtils.get(token);
        User user = JSON.parseObject(jsonUser,User.class);
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setContent(content);
        comment.setCreateUser(user.getId());
        boolean b = service.saveComment(comment);
        ResultDto resultDto = new ResultDto();
        if (b){
            resultDto.setCode(200);
            resultDto.setMsg("评论成功");
        }else {
            resultDto.setCode(500);
            resultDto.setMsg("评论失败");
        }
        return resultDto;
    }


    private void saveRedis(User user,String token){

        String tokenOld = (String) redisUtils.get(user.getId()+"");
        if (tokenOld!=null){
            redisUtils.delete(user.getId()+"");
            redisUtils.delete(tokenOld);
        }

        redisUtils.set(user.getId()+"",3600,token);
        String jsonUser = JSON.toJSONString(user);
        redisUtils.set(token,3600,jsonUser);
    }

    private String getToken(User user){
        StringBuilder builder = new StringBuilder();
        builder.append("token-");
        builder.append(user.getId()+"-");
        builder.append(new Date().getTime());
        return builder.toString();
    }

}
