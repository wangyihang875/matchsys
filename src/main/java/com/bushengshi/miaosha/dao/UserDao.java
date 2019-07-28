package com.bushengshi.miaosha.dao;

import com.bushengshi.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from t_user where id = #{id}")
    public User getById(@Param("id")int id);

    @Insert("insert into t_user(id,name) values(#{id},#{name})")
    public int insert(User user);
}
