package com.itheima;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestMP {

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据id查询
     */
    @Test
    public void testSelectById() { 
        User user = userMapper.selectById(1l);
        System.out.println(user);
    }

    @Test
    public void testInsert() {
        User user =
                User.builder()
//                        .userName("itheima")
                        .name("itcast")
                        .age(15)
                        .email("itcast@itcast.cn")
                        .password("111111")
                        .build();
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void testDelete(){
//        int count = userMapper.deleteById(8L);
        List ids = new ArrayList();
        ids.add(6);
        ids.add(7);
        userMapper.deleteBatchIds(ids);
    }

    @Test
    public void testUpdateById() {
        User user = new User();
        user.setId(2L);
        user.setPassword("1111111");
        int count = userMapper.updateById(user);
    }

    /**
     * 分页查询：
     *  1. 当前页码：currentPage
     *  2. 每页显示条数：size
     *
     *  注意：使用mp的分页要设置一个拦截器！！！
     */
    @Test
    public void testSelectPage() {
        int current = 1;//当前页码
        int size = 2;//每页显示条数
        IPage<User> page = new Page(current,size);
        userMapper.selectPage(page,null);
        List<User> records = page.getRecords();//当前页的数据
        long pages = page.getPages();//总页数 7
        long total = page.getTotal();//总记录数 14
        System.out.println(records);
        System.out.println(pages);
        System.out.println(total);
    }

    /**
     * @Description 测试条件查询
     * 要求：查询用户中姓名包含"伤"，密码为"123456",且年龄为19或者25或者29，查询结果按照年龄降序排序；
     * 如果查询的条件过于复杂，mp还适合么？
     *        简单的操作，直接使用mp
     *        但是非常复杂的操作，比如多表关联查询 复杂条件查询等，建议使用xml下sql实现；
     */
    @Test
    public void testcQuery1(){
        //构建添加包装对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置条件
        wrapper.like("user_name","伤")
                .eq("password","123456")
                .in("age",19,25,29)
                //.in("age", Arrays.asList(19,25,29))
                .orderByDesc("age");
        //查询
        /*
        ==> Preparing: SELECT id,user_name,password,name,age,email FROM tb_user WHERE (user_name LIKE ? AND password = ? AND age IN (?,?,?)) ORDER BY age DESC
        ==> Parameters: %伤%(String), 123456(String), 19(Integer), 25(Integer), 29(Integer)
         */
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void testWrapper2(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .or()
                .lt("age",23);
    /*
       select * from tb_user where user_name = ? or age < ?
    */
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    /**
     * 模糊查询
     */
    @Test
    public void testWrapper3(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.likeLeft("user_name","zhang");
        /*
            SELECT id,user_name,password,name,age,email
             from tb_user
             where user_name like ?
             %zhang
         */
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void testWrapper4(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper.eq("user_name","lisi")
                .or()
                .lt("age",23)
                .in("name","李四","王五")
                //.orderBy(true,true,"age")
                .orderByDesc("age");
        /*
            select * from tb_user where user_name = ? or age < ? and name in (?,?) order by age asc
         */
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void testWrapper5(){
        //1.创建查询条件构建器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //2.设置条件
        wrapper
//                .eq("user_name","lisi")
//                .or()
//                .lt("age",23)
//                .in("name","李四","王五")
                //.orderBy(true,true,"age")
                .orderByDesc("age")
                .select("id","user_name as realName");
        /*
            select id,user_name from tb_user where user_name = ? or age < ? and name in (?,?) order by age asc
         */
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    @Test
    public void testWrapper6(){
        int current = 2;//当前页码
        int size = 3;//每页显示条数
        //1. 构建分页对象
        Page<User> page = new Page<>(current,size);
        //2. 构建条件对象
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.gt("age",23);
        userMapper.selectPage(page,wrapper);
        List<User> records = page.getRecords();
        long total = page.getTotal();
        long pages = page.getPages();
        System.out.println(records);
        System.out.println(total);//2
        System.out.println(pages);//1
    }

    @Test
    public void testWrapper7() throws Exception{
        // LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        User user = new User();

        //        wrapper.like("user_name", "%伤%")
        //                .eq("password","123456")
        //                .ge("age", 28)
        //                .between("age",29 , 39);  // 包含边界值
        wrapper.like(User::getRealName, "%伤%")
                .eq(User::getPassword, "123456")
                .ge(User::getAge, 28)
                .between(User::getAge, 29, 39)
                .orderByDesc(User::getAge)
                .select(User::getId, User::getRealName);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }

    /**
     * 条件删除
     * @throws Exception
     */
    @Test
    public void testWrapper8() throws Exception{

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getRealName, "赵敏");
        int i = userMapper.delete(wrapper);
        System.out.println(i);
    }


    /**
     * 条件更新
     * @throws Exception
     */
    @Test
    public void testWrapper10() throws Exception{
        User user = userMapper.selectById(2l);

        /**
         * UPDATE tb_user SET t_name=? WHERE (id = ?)
         */
        // 参数1： 最新的值
        user.setRealName("张丰");

        // 参数2：更新时条件
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        wrapper.eq(User::getId, 2l);

        int update = userMapper.update(user, wrapper);
        System.out.println(update);
    }


    /**
     * 条件更新
     * @throws Exception
     */
    @Test
    public void testWrapper9() throws Exception{
        /**
         * UPDATE tb_user SET t_name=?, user_name=? WHERE (id = ?)
         */
        // 参数1： 最新的值
        // 参数2：更新时条件
        LambdaUpdateWrapper<User> wrapper = Wrappers.<User>lambdaUpdate();
        wrapper.eq(User::getId, 15)
                .set(User::getRealName, "张三丰666")
                .set(User::getName,"zsf666");

        int update = userMapper.update(null, wrapper);
        System.out.println(update);
    }

    /**
     * @Description 自定义sql分页查询实现
     */
    @Test
    public void test19(){
        IPage<User> page=new Page<>(2,3);
        IPage<User> users = userMapper.findGtIdByPage(page, 3l);
        System.out.println(users.getRecords());
        System.out.println(users.getPages());
        System.out.println(users.getTotal());
    }
}