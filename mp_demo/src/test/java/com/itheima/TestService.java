package com.itheima;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author jensen
 * @date 2024-09-21 13:51
 * @description
 */
@SpringBootTest
public class TestService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Test
    public void test1(){
        User byId = userService.getById(4l);
        System.out.println("byId = " + byId);
    }

    /**
     * @Description 测试条件查询，且仅返回一个
     * getOne:sql查询的结果必须为1条或者没有，否则报错 ！！！！
     */
    @Test
    public void test2(){
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
//        wrapper.gt(User::getAge,20);
        wrapper.eq(User::getId,4l);
        User one = userService.getOne(wrapper);
        System.out.println(one);
    }

    /**
     * @Description 根据条件批量查询
     */
    @Test
    public void test3(){
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.gt(User::getAge,20);
        List<User> list = userService.list(wrapper);
        System.out.println(list);
    }

    /**
     * @Description 根据条件批量查询并分页
     */
    @Test
    public void test4(){
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.gt(User::getAge,20);
        //构建分页对象
        IPage<User> page=new Page<>(2,3);
        userService.page(page,wrapper);
        System.out.println(page.getRecords());
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
    }

    /**
     * @Description 测试服务层save保存单条操作
     */
    @Test
    public void test5(){
        User user1 = User.builder().name("wangwu").realName("laowang4").
                email("444@163.com").age(20).password("333").build();
        boolean isSuccess = userService.save(user1);
        System.out.println(isSuccess?"保存成功":"保存失败");
    }

    /**
     * @Description 测试服务层批量保存
     */
    @Test
    public void test6(){
        User user2 = User.builder().name("wangwu2").realName("laowang2").
                email("444@163.com").age(20).password("333").build();
        User user3 = User.builder().name("wangwu3").realName("laowang3").
                email("444@163.com").age(20).password("333").build();
        boolean isSuccess = userService.saveBatch(Arrays.asList(user2, user3));
        System.out.println(isSuccess?"保存成功":"保存失败");
    }

    /**
     * @Description 根据id删除操作
     */
    @Test
    public void test7(){
        boolean isSuccess = userService.removeById(17l);
        System.out.println(isSuccess?"保存成功":"保存失败");
    }

    /**
     * @Description 根据条件批量删除
     */
    @Test
    public void test8(){
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class);
        wrapper.gt(User::getId,12)
                .gt(User::getAge,20);
        boolean remove = userService.remove(wrapper);
        System.out.println(remove);
    }

    /**
     * @Description 测试根据id更新数据
     */
    @Test
    public void test9(){
        //UPDATE tb_user SET password=?, t_name=? WHERE id=?
        User user2 = User.builder().name("wangwu2").password("333").id(3l).build();
        boolean success = userService.updateById(user2);
        System.out.println(success);
    }

    /**
     * @Description 测试根据条件批量更新
     */
    @Test
    public void test10(){
        LambdaUpdateWrapper<User> wrapper = Wrappers.lambdaUpdate(User.class);
        //UPDATE tb_user SET age=? WHERE (id IN (?,?,?))
        wrapper.in(User::getId, Arrays.asList(1l,3l,5l)).set(User::getAge,40);
        boolean update = userService.update(wrapper);
        System.out.println(userService);
    }

    @Test
    public void testDelete(){
        //根据id删除
        int count = userMapper.deleteById(12l);
        System.out.println(count);
    }
}
