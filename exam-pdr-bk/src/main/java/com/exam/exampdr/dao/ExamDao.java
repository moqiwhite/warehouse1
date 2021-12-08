package com.exam.exampdr.dao;

import model.Comment;
import model.Detail;
import model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamDao {

    List<Detail> getAll();

    User getUser(@Param("userName")String userName,@Param("password")String password);

    int addComment(Comment comment);

}
