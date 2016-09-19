package com.juliovaz.condio.model;

import java.io.Serializable;

/**
 * Created by julio on 08/05/16.
 */
public class Device implements Serializable {

    private String name;
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
