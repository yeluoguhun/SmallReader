package com.hanshaoda.smallreader.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author: hanshaoda
 * created on: 2017/9/9 下午1:55
 * description:
 */

@Entity
public class CategoryEntity {

    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String key;
    private int order;

    @Generated(hash = 747555915)
    public CategoryEntity(Long id, String name, String key, int order) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.order = order;
    }

    @Generated(hash = 725894750)
    public CategoryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        if (order != that.order) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return key != null ? key.equals(that.key) : that.key == null;

    }
}
