package com.tx.coin.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="create_time")
    private Date createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
