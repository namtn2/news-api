package com.api.news.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NEWS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News implements Cloneable {

    @Column(name = "ID")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ACTIVE")
    private Long active;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Override
    public News clone() {
        try {
            return (News) super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}
