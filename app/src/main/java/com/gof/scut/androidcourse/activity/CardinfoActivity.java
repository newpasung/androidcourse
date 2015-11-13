package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private TextView mTvName;
    private TextView mTvCompany;
    private TextView mTvPhone;
    private Button mBtnShowQrcode;
    private Card mCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);
        iniUI();
        showInfo();
        iniListener();
    }

    private void iniUI() {
        mIvAvatar = (ImageView) findViewById(R.id.iv_avatar);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvCompany = (TextView) findViewById(R.id.tv_company);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mBtnShowQrcode = (Button) findViewById(R.id.btn_show_qrcode);
    }

    private void iniListener() {
        mBtnShowQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardinfoActivity.this, ShowQRcodeActivity.class);
                if(getIntent().getBundleExtra("data") != null) {
                    int index = getIntent().getBundleExtra("data").getInt("index");
                    Bundle bundle = new Bundle();
                    bundle.putInt("index", index);
                    intent.putExtra("data", bundle);
                }
                startActivity(intent);
            }
        });
    }

    private void showInfo() {
        if(getIntent().getBundleExtra("data")!=null){
            Bundle data =getIntent().getBundleExtra("data");
            int index =data.getInt("index");
            mCard = ((MyApplication)getApplicationContext()).getCard(index);
        }
        else{
            mCard = ((MyApplication)getApplicationContext()).getMyCard();
        }
        mTvName.setText(String.format(getString(R.string.name_format), mCard.getName()));
        mTvCompany.setText(String.format(getString(R.string.company_format),mCard.getCompany()));
        mTvPhone.setText(String.format(getString(R.string.phone_format),mCard.getPhonenumber1()));
        mIvAvatar.setImageResource(R.drawable.user_avatar);
    }
}
