package com.example.administrator.bmob;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/30.
 */

public class Student extends BmobObject{
    String name;
    String address;
    int age;
    int belongclass;
    public Student(){

    }
    public Student(String name,int age,String address,int belongclass){
        this.name=name;
        this.address = address;
        this.age = age;
        this.belongclass = belongclass;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return address;
    }
    public void setAge(int age){
        this.age=age;
    }
    public int getAge(){
        return age;
    }
    public void setBelongclass(int belongclass){
        this.belongclass = belongclass;
    }
    public int getBelongclass(){
        return belongclass;
    }
}
