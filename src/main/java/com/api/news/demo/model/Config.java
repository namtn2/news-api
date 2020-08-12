package com.api.news.demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONFIG")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Config {

    @Column(name = "GROUP")
    private String group;

    @Id
    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;
}
