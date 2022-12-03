package com.ab.entity;

public class Person {

    String name;
    String address;
    String mobileNo;
    String emailId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public static  <T> T showDetails(java.lang.Class<T>tClass, int id){
        if(tClass == Student.class){
            /// return student class object
        }else{
            // return customer class object
        }
        return  null;
    }

}
