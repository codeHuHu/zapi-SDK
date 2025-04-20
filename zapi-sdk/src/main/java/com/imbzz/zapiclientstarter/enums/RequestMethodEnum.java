package com.imbzz.zapiclientstarter.enums;

/**
 * @author imbzz
 * @Date 2024/4/29 23:31
 */
public enum RequestMethodEnum {

    GET("GET", "GET"),
    POST("POST","POST");

    private final String value;
    private final String text;

    RequestMethodEnum(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText(){
        return text;
    }
}
