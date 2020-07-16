package com.gsp.springcloud.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.gsp.springcloud.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author Don
 * @Description User实体
 * @Date 2020/7/14 20:38
 **/
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Table(name = "t_user")
public class User extends BaseModel implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号", width = 5, orderNum = "1")
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @Excel(name = "用户名", width = 45, orderNum = "2")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 部门ID
     */
    @Column(name = "dept_id")
    @Excel(name = "部门编号", width = 5, orderNum = "3")
    private Long deptId;

    /**
     * 邮箱
     */
    @Column(name = "email")
    @Excel(name = "邮箱", width = 20, orderNum = "4")
    private String email;

    /**
     * 联系电话
     */
    @Column(name = "mobile")
    @Excel(name = "联系电话", width = 15, orderNum = "5")
    private String mobile;

    /**
     * 状态 0锁定 1有效
     */
    @Column(name = "mobile")
    @Excel(name = "状态", width = 10, replace = { "锁定_0", "有效_1"},orderNum = "6")
    private String status;

    @Column(name = "create_time")
    @Excel(name = "创建时间", width = 25, orderNum = "7")
    private String createTime;

    @Column(name = "modify_time")
    @Excel(name = "修改时间", width = 25, orderNum = "8")
    private String modifyTime;
    /**
     * 最近访问时间
     */

    @Column(name = "last_login_time")
    private String lastLoginTime;

    /**
     * 性别 0男 1女 2保密
     */
    @Column(name = "ssex")
    @Excel(name = "性别", width = 10, replace = { "男_0", "女_1","保密_2" },orderNum = "9")
    private String ssex;

    /**
     * 描述
     */
    private String description;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户类型 0:单位用户 1:审核用户 2:管理员
     */
    private String type;

    /**
     * 无状态token值
     */
    private String token;

}