package com.itheima.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.entity.TbUser;
import com.itheima.service.TbUserService;
import com.itheima.mapper.TbUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 59484
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2024-09-21 16:05:17
*/
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser>
    implements TbUserService{

}




