package com.example.administrator.bean;

/**
 * Created by Administrator on 2017/10/25.
 */

public class City {
    private String province;
    private String city;
    private String number;
    private String firstPY;
    private String allPY;
    private String allFristPY;
//定义City的结构
public City(String province,String city,String number,String firstPY,String allPY,String allFristPY){
    this.province = province;
    this.city = city;
    this.number = number;
    this.allFristPY =allFristPY;
    this.firstPY = firstPY;
    this.allPY = allPY;
}

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }
}