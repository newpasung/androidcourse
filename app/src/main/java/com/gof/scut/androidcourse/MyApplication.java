package com.gof.scut.androidcourse;

import android.app.Application;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/10.
 */
public class MyApplication extends Application {

    List<Card> cardList;
    @Override
    public void onCreate() {
        super.onCreate();
        iniData();
    }

    private void iniData(){
        cardList = new ArrayList<>();
        //第一张是自己
        cardList.add(new Card("郭佳哲","10086"));
        cardList.add(new Card("刘德华","18814166584"));
        cardList.add(new Card("张学友","18816467834"));
        cardList.add(new Card("周润发","15487624684"));
        cardList.add(new Card("张三","15487624684"));
        cardList.add(new Card("李四","15487624684"));
        cardList.add(new Card("小明","15487624684"));
        cardList.add(new Card("小红","15487624684"));
        cardList.add(new Card("张伟","15487624684"));
        cardList.add(new Card("王强", "15487624684"));
        cardList.add(new Card("啊哈", "15487624684"));
    }

    public List<Card> getCardList(){
        if(cardList==null){
            iniData();
        }
        return this.cardList;
    }

    public Card getMyCard(){
        if(cardList==null)iniData();
        return cardList.get(0);
    }

    public Card getCard(int index){
        if(cardList==null){
            iniData();
        }
        return cardList.get(index);
    }

}
