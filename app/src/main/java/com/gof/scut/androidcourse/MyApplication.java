package com.gof.scut.androidcourse;

import android.app.Application;
import android.util.ArrayMap;

import com.activeandroid.ActiveAndroid;

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
        ActiveAndroid.initialize(this);
        iniData();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    private void iniData(){
        cardList = new ArrayList<>();
        //第一张是自己
        cardList.add(new Card("郭佳哲","18814122509", "华南理工大学", Card.CardColor.BLUE));
        cardList.add(new Card("刘德华","18814166584", "华南理工大学", Card.CardColor.YELLOW));
        cardList.add(new Card("张学友","18816467834", "华南理工大学", Card.CardColor.WHITE));
        cardList.add(new Card("周润发","15487624684", "华南理工大学", Card.CardColor.PINK));
        cardList.add(new Card("张三","15487624684", "华南理工大学", Card.CardColor.GREEN));
        cardList.add(new Card("李四","15487624684", "华南理工大学", Card.CardColor.BLUE));
        cardList.add(new Card("小明","15487624684", "华南理工大学", Card.CardColor.WHITE));
        cardList.add(new Card("小红","15487624684", "华南理工大学", Card.CardColor.GREEN));
        cardList.add(new Card("张伟","15487624684", "华南理工大学", Card.CardColor.PINK));
        cardList.add(new Card("王强", "15487624684", "华南理工大学", Card.CardColor.WHITE));
        cardList.add(new Card("啊哈", "15487624684", "华南理工大学", Card.CardColor.BLUE));
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
