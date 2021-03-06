package com.li2n.im_server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li2n.im_server.entity.FriendList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 * 好友列表 Mapper 接口
 * </p>
 *
 * @author Li2N
 * @since 2022-03-08
 */
@Mapper
@Repository
public interface FriendListMapper extends BaseMapper<FriendList> {

    /**
     * 根据用户名更新好友列表
     *
     * @param username
     * @param friends
     * @param updateTime
     */
    void updateFriendsByUsername(@Param("username") String username, @Param("friends") String friends, @Param("updateTime") LocalDateTime updateTime);

}
