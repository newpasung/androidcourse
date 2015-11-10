package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.gof.scut.androidcourse.Card;
import com.gof.scut.androidcourse.MyApplication;
import com.gof.scut.androidcourse.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CardinfoActivity extends Activity {

    private ImageView mIvAvatar;
    private TextView mTvname;
    private TextView mTvaddress;
    private TextView mTvphone;
    Card mCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);
        iniUI();
        showInfo();
    }

    protected void iniUI(){
        mIvAvatar=(ImageView)findViewById(R.id.iv_avatar);
        mTvname=(TextView)findViewById(R.id.tv_name);
        mTvphone=(TextView)findViewById(R.id.tv_phone);
        mTvaddress=(TextView)findViewById(R.id.tv_address);
    }

    private void showInfo(){
        if(getIntent().getBundleExtra("data")!=null){
            Bundle data =getIntent().getBundleExtra("data");
            int index =data.getInt("index");
            mCard = ((MyApplication)getApplicationContext()).getCard(index);
        }
        else{
            mCard = ((MyApplication)getApplicationContext()).getMyCard();
        }
        mTvname.setText(String.format(getString(R.string.name_format),mCard.getName()));
        mIvAvatar.setImageResource(R.drawable.user_avatar);
    }
}
