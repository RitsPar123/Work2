package com.flipkart.service;

import java.util.Date;

public class User {
    public  int UserId;
    public  String name;
    public  long intime;
    public  String number;
    public  int price;
    public int callattempt;
    public int missedcall;
    public boolean inwtschedq;
    public boolean inrtoq2;
    public User(int id,String name,String number,int price)
    {
        this.UserId=id;
        this.name=name;

        Date date=new Date();
        this.intime= date.getTime();

        this.number=number;
        this.price=price;
        this.callattempt=0;
        this.missedcall=0;
        this.inwtschedq=false;
        this.inrtoq2=false;
    }



}
