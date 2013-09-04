package com.genetor.base;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-4
 * Time: 上午10:35
 * To change this template use File | Settings | File Templates.
 */
public class RegexField {
    private String name;
    private String key;

    public RegexField(String key, String name) {
        this.key = key;
        this.name = name;
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

    public String toString() {
        return name;
    }
}
