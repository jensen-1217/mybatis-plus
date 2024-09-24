package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author jensen
 * @date 2024-09-20 16:39
 * @description
 */
//@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<User> findGtIdByPage(IPage<User> page, @Param("id") Long id);
}
