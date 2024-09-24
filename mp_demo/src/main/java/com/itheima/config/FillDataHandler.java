package com.itheima.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author by itheima
 * @Date 2022/8/12
 * @Description
 */
@Component
public class FillDataHandler implements MetaObjectHandler {

    /**
     * 定义自动填充的方法
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置insert操作时的时间点
        metaObject.setValue("createTime",new Date());
        //设置update操作时的时间点
        metaObject.setValue("updateTime",new Date());
    }

    /**
     * 定义更新时填充的方法
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //设置update操作时的时间点
        metaObject.setValue("updateTime",new Date());
    }
}
