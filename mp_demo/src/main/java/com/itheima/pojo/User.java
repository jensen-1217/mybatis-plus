package com.itheima.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author by itheima
 * @Description 实体类基于注解与表进行映射
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_user")
public class User {
    @TableId(value = "id",type= IdType.AUTO)//字段不一致时，通过value值指定table中主键字段名称
    private Long id;
    @TableField(value = "user_name")
    private String realName;
    private String password;
    private String name;
    private Integer age;
    private String email;
    @TableField(exist = false)
    private String address;
    //......
    @TableLogic//指定逻辑删除字段
    private Integer deleted;
    //......
    @Version
    private Integer version;
    //指定插入时自动填充的字段
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
    //自定插入或者更新时自动填充的字段
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
