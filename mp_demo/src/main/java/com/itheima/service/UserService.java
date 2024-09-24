package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.pojo.User;

/**
 * @author jensen
 * @date 2024-09-21 11:11
 * @description
 */
public interface UserService extends IService<User> {
    public IPage<User> getPageINfo(IPage<User> page,Long id);
}
