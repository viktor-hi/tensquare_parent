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
    //将自增长的id查询到设置到user对象中
    @Insert("insert into user values (null,#{username},#{birthday},#{sex},#{address})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = Integer.class,before = false,statement = "SELECT last_insert_id()")
    int addUser(User user);

    @Delete("delete from user where id=#{id}")
    int deleteUserById(int id);

    @Update("update user set username=#{username},address=#{address} where id=#{id}")
    int updateUser(User user);

    @Select("select * from user where id=#{id}")
    User findUserById(int id);

    //使用Results注解自定义映射规则,它里面的每一个Result子标签就映射一个字段

    @Results(id="UserInfoId",value = {
            //id = true表示映射的这个字段是主键
            @Result(column = "id",property = "userId",id = true),
            //如果使用内置映射规则能够映射好的字段，我们可以不进行自定义映射
            //@Result(column = "username",property = "userName"),
            @Result(column = "sex",property = "userSex"),
            @Result(column = "address",property = "userAddress"),
            @Result(column = "birthday",property = "userBirthday")
    })
    @Select("select * from user")
    List<UserInfo> findAll();

    /**
     * 根据id查询用户信息，并且将结果集映射到UserInfo对象中
     * @param id
     * @return
     */
    //使用ResultMap注解引用自定义映射规则
    @ResultMap("UserInfoId")
    @Select("select * from user where id=#{id}")
    UserInfo findUserInfoById(int id);
}
