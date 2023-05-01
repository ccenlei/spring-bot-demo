package com.spring.bot.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.spring.bot.demo.entity.MintUser;

/**
 * @Select("select * from t_user where id=#{id}")
 * 
 * @Results({@Result(property = "userName",column =
 *                            "user_name"),@Result(property = "userSex",column =
 *                            "user_sex")})
 */
@Mapper
public interface MintUserMapper {

    @Select("SELECT * FROM mint_fun_user WHERE id = #{id}")
    MintUser selectUserById(@Param("id") Integer id);

    @Select("SELECT * FROM mint_fun_user WHERE name = #{name}")
    List<MintUser> selectUserByName(@Param("name") String name);

    @Select("SELECT * FROM mint_fun_user")
    List<MintUser> selectAll();

    @Insert("INSERT INTO mint_fun_user (name, addr, following, followers) VALUES (#{name}, #{addr}, #{following}, #{followers})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertUser(MintUser mUser);

    @Delete("DELETE FROM mint_fun_user WHERE id = #{id}")
    void deleteUserById(@Param("id") Integer id);

    @Update("UPDATE mint_fun_user SET following=#{following},followers=#{followers} WHERE id = #{id}")
    void updateUserById(MintUser mUser);

    MintUser selectUserDetailById(Integer id);
}
