package com.imbzz.zapiclientstarter.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author imbzz
 * @Date 2023/11/29 11:47
 */
@Data
public class User implements Serializable {
    private String userName;

    public User() {
    }
    public User(String userName) {
        this.userName = userName;
    }
}
