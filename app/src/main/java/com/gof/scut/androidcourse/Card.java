package com.gof.scut.androidcourse;

/**
 * Created by Administrator on 2015/11/8.
 */
public class Card {

    private String avatarurl;
    private String name;
    private String phonenumber1;
    private String phonenumber2;
    private String email;
    private String address;
    public Card(String name, String phonenumber1) {
        this.name = name;
        this.phonenumber1 = phonenumber1;
        this.phonenumber2 ="";
        this.email="";
        this.address="";
        this.avatarurl="";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber1() {
        return phonenumber1;
    }

    public void setPhonenumber1(String phonenumber1) {
        this.phonenumber1 = phonenumber1;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getAdress() {
        return address;
    }

    public void setAdress(String adress) {
        this.address = adress;
    }

    public String getAvatarurl() {
        if (avatarurl==null){
            avatarurl="";
        }
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }
}
