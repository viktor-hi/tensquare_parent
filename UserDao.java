package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.domain.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 包名:com.itheima.dao
 * 作者:Leevi
 * 日期2019-08-13  10:48
 * 使用注解进行增删改查
 * 1. 方法是添加用户，则在该方法上添加Insert注解，在注解中写SQL语句
 * 2. 方法是删除用户，则在该方法上添加Delete注解，在注解中写SQL语句
 * 3. 方法是修改用户，则在该方法上添加Update注解，在注解中写SQL语句
 * 4. 方法是查询用户，则在该方法上添加Select注解，在注解中写SQL语句
 */
@CacheNamespace
public interface UserDao {
   
}
