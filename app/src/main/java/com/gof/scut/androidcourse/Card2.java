package com.gof.scut.androidcourse;

import android.content.Context;
import android.graphics.Color;

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

    @Column(name = "carid")
    public long cardid;
    @Column(name = "islocalcard")
    public int islocalcard;
    @Column(name = "userid")
    public long userid;
    @Column(name = "name")
    public String name;
    @Column(name = "phonenumber")
    public String phonenumber;
    @Column(name = "company")
    private String company;

    public static Card2 insertOrUpdate(JSONObject data) {
        Card2 card = null;
        long cardid = 0;
        if (data.has("cardid")) {
            try {
                cardid = data.getLong("cardid");
                if (getCard(cardid) == null) {
                    card = new Card2();
                    card.cardid = cardid;
                } else {
                    card = getCard(cardid);
                }
                card.userid = data.getLong("uid");
                card.islocalcard = data.getInt("islocalcard");
                card.company = data.getString("company");
                card.name = data.getString("name");
                card.phonenumber = data.getString("phone");
                card.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return card;
    }

    public static List<Card2> insertOrupdate(JSONArray dataArray) {
        List<Card2> card2s = new ArrayList<>();
        Card2 temp;
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < dataArray.length(); i++) {
                temp = Card2.insertOrUpdate(dataArray.getJSONObject(i));
                if (temp != null) {
                    card2s.add(temp);
                }
            }
            ActiveAndroid.setTransactionSuccessful();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return card2s;
    }

    public static Card2 getCard(long cardid) {
        return new Select().from(Card2.class).where("carid=" + cardid).executeSingle();
    }

    public int getBackgroundColor() {
        return Color.WHITE;
    }

    public String getCompany() {
        return this.company == null ? "" : this.company;
    }

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public String getPhonenumber() {
        return this.phonenumber == null ? "" : this.phonenumber;
    }

    public long getCardid() {
        return cardid;
    }

    public int getTextColor() {
        return Color.BLACK;
    }

    public long getUserid() {
        return userid;
    }

    public int getIslocalcard() {
        return islocalcard;
    }

    public static Card2 getMyCard(Context context) {
        long uid = XManager.getUid(context);
        return new Select().from(Card2.class).where("userid=" + uid).executeSingle();
    }

    public static long getMyCardid(Context context) {
        return getMyCard(context).getCardid();
    }

    public static void deleteCard(long cardid) {
        getCard(cardid).delete();
    }

}
