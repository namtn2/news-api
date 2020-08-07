package com.api.news.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CATEGORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category implements Cloneable {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACTIVE")
    private Long active;

    @Column(name = "IMG_URL")
    private String imgUrl;

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (Exception e) {
            return null;
        }
    }
}
