package com.gof.scut.androidcourse;

import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.gof.scut.androidcourse.storage.XManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/13.
 */
@Table(name = "card2")
public class Card2 extends Model {

    @Column(name ="carid")
    public long cardid;
    @Column (name ="islocalcard")
    public boolean islocalcard;
    @Column (name ="userid")
    public long userid;
    @Column(name = "name")
    public String name;
    @Column(name = "phonenumber")
    public String phonenumber;

    public static Card2 insertOrUpdate(JSONObject data){
        Card2 card =null;
        long cardid =0;
        if(data.has("cardid")){
            try {
                cardid=data.getLong("cardid");
                if(getCard(cardid)==null){
                    card =new Card2();
                    card.cardid =cardid;
                }else{
                    card =getCard(cardid);
                }
                card.userid =data.getLong("userid");
                card.islocalcard=data.getBoolean("islocalcard");
                card.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    public static List<Card2> insertOrupdate(JSONObject data){
        List<Card2> card2s =new ArrayList<>();
        Card2 temp ;
        ActiveAndroid.beginTransaction();
        try {
            JSONArray dataArray =data.getJSONArray("card2");
            for (int i=0;i<dataArray.length();i++){
                temp =Card2.insertOrUpdate(dataArray.getJSONObject(i));
                if(temp!=null){
                    card2s.add(temp);
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
        return card2s;
    }

    public static Card2 getCard(long cardid){
        return new Select().from(Card2.class).where("carid="+cardid).executeSingle();
    }

    public static Card2 createLocalCard(String name ,String phonenumber){
        Card2 card =new Card2();
        card.name=name;
        card.phonenumber =phonenumber;
        return card;
    }

    public void commitLocal(Context context){
        this.islocalcard=true;
        this.userid = XManager.getUid(context);
        this.save();
        Log.i("sqlmax",new Select("max(cardid)").toSql());
    }

}
