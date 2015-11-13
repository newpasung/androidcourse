package com.gof.scut.androidcourse;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 2015/11/8.
 */
@Table(name = "card")
public class Card extends Model{

    @Column (name ="carid")
    public long cardid;
    @Column (name ="islocalcard")
    public boolean islocalcard;
    @Column (name ="userid")
    public long userid;

    private String avatarurl;
    private String name;
    private String phonenumber1;
    private String phonenumber2;
    private String email;
    private String address;
    private String company;

    private int backgroundColor;
    private int textColor;

    public Card(String name, String phonenumber1, String company, int color) {
        this.name = name;
        this.phonenumber1 = phonenumber1;
        this.company = company;
        this.phonenumber2 ="";
        this.email="";
        this.address="";
        this.avatarurl="";

        switch (color)
        {
            case CardColor.BLUE:
                backgroundColor = R.color.blue;
                textColor = R.color.white;
                break;
            case CardColor.GREEN:
                backgroundColor = R.color.green;
                textColor = R.color.white;
                break;
            case CardColor.PINK:
                backgroundColor = R.color.pink;
                textColor = R.color.white;
                break;
            case CardColor.WHITE:
                backgroundColor = R.color.white;
                textColor = R.color.black;
                break;
            case CardColor.YELLOW:
                backgroundColor = R.color.yellow;
                textColor = R.color.black;
                break;
            default:
                backgroundColor = R.color.white;
                textColor = R.color.black;
                break;
        }
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


    public String getCompany() {
        return company;
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


    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getTextColor() {
        return textColor;
    }

    class CardColor {
        public static final int BLUE = 1;
        public static final int GREEN = 2;
        public static final int YELLOW = 3;
        public static final int PINK = 4;
        public static final int WHITE = 5;
    }
}
