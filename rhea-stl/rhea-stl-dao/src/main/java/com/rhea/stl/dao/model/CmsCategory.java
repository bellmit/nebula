package com.rhea.stl.dao.model;

import javax.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table(name = "cms_category")
public class CmsCategory {
    /**
     * 类目编号
     */
    @Id
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 上级编号
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 层级
     */
    @Column(name = "level")
    private Byte level;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 类型(1:普通,2:热门...)
     */
    @Column(name = "type")
    private Byte type;

    /**
     * 别名
     */
    @Column(name = "alias")
    private String alias;

    /**
     * 所属系统
     */
    @Column(name = "system_id")
    private Integer systemId;

    /**
     * 创建时间
     */
    @Column(name = "ctime")
    private Long ctime;

    /**
     * 排序
     */
    @Column(name = "orders")
    private Long orders;
}