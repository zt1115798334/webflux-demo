package com.example.webfluxdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/11/30 11:39
 * description:
 */
@Document
@Data
public class User {

    @Id
    private String id;

    private String name;

    private Integer age;

}
