package com.feitian.trade.sync.dao;

import com.feitian.trade.sync.model.TbUser;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface TbUserMapper {
    @Insert("insert into tb_shop(userId, nick, lastUpdateTime, expiresin, expiresinR1, expiresinR2, expiresinW1, expiresinW2, reexpiresin, authorizeTime) values" +
            "(#{user.userId}, #{user.nick}, #{user.lastUpdateTime}, #{user.expiresin}, #{user.expiresinR1}," +
            " #{user.expiresinR2}, #{user.expiresinW1}, #{user.expiresinW2}, #{user.reexpiresin}, #{user.authorizeTime)}")
    int add(@Param("user") TbUser user);

    @Update("update tb_shop set sessionKey = #{user.sessionKey}, expiresin = #{user.expiresin}, expiresinR1 = #{user.expiresinR1}, expiresinR2 = #{user.expiresinR2}," +
            " expiresinW1 = #{user.expiresinW1}, expiresinW2 = #{user.expiresinW2}, reexpiresin = #{user.reexpiresin}, authorizeTime = #{user.authorizeTime} where userId = #{user.userId}")
    int updateAuthorizeInfo(@Param("user") TbUser user);

    @Update("update tb_shop set lastUpdateTime = #{lastUpdateTime} where userId = #{userId}")
    int updateLastUpdateTime(@Param("userId") Long userId, @Param("lastUpdateTime") Date lastUpdateTime);

    @Delete("delete from tb_shop where userId = #{userId}")
    int delete(int userId);

    @Select("select * from tb_shop where userId = #{userId}")
    TbUser findUser(@Param("id") Long userId);

    @Select("select * from tb_shop where lastUpdateTime is not null order by lastUpdateTime limit #{limit}")
    List<TbUser> findIncrementUsers(Long limit);

    @Select("select * from tb_shop order by lastUpdateTime")
    List<TbUser> findAllUsers();
}
