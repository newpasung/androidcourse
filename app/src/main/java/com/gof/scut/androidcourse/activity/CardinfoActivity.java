package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CardinfoActivity extends Activity {

    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvCompany;
    private TextView mTvPhone;
    private Button mBtnShowQrcode;
    private Card2 mCard;
    Button mBtndelete;
    private final static int MSG_SHOWINFO = 0;
    private final static int MSG_CLOSEACT = 1;
    private ProgressDialog progressDialog;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SHOWINFO: {
                    mTvName.setText(String.format(getString(R.string.name_format), mCard.getName()));
                    mTvCompany.setText(String.format(getString(R.string.company_format), mCard.getCompany()));
                    mTvPhone.setText(String.format(getString(R.string.phone_format), mCard.getPhonenumber()));
                    mIvAvatar.setImageResource(R.drawable.user_avatar);
                }
                break;
                case MSG_CLOSEACT: {
                    finish();
                }
                break;
            }
        }
    };

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
        mBtndelete = (Button) findViewById(R.id.btn_delete);
    }

    private void iniListener() {
        mBtnShowQrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardinfoActivity.this, ShowQRcodeActivity.class);
                if (getIntent().getBundleExtra("data") != null) {
                    long cardid = getIntent().getBundleExtra("data").getLong("cardid");
                    Bundle bundle = new Bundle();
                    bundle.putLong("cardid", cardid);
                    intent.putExtra("data", bundle);
                }
                startActivity(intent);
            }
        });
        mBtndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCard == null) {
                    return;
                }
                progressDialog = ProgressDialog.show(CardinfoActivity.this, "", "deleting", true);
                deleteCard();
            }
        });
    }

    private void showInfo() {
        if (getIntent().getBundleExtra("data") != null) {
            long cardid = getIntent().getBundleExtra("data").getLong("cardid");
            mCard = Card2.getCard(cardid);
            if (mCard == null) {
                getNetData();
                return;
            }
        } else {
            mCard = Card2.getMyCard(this);
            mBtndelete.setVisibility(View.INVISIBLE);
        }
        Message msg = new Message();
        msg.what = MSG_SHOWINFO;
        mHandler.sendMessage(msg);
    }

    private void deleteCard() {
        RequestParams params = new RequestParams();
        final long id = mCard.getCardid();
        params.put("cardid", id);
        HttpClient.post(this, "card/delete", params, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Card2.deleteCard(id);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Message msg = new Message();
                msg.what = MSG_CLOSEACT;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(String message, String for_param) {
                Toast.makeText(CardinfoActivity.this, "fetching failed", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void getNetData() {
        RequestParams params = new RequestParams();
        HttpClient.get(this, "", params, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Message msg = new Message();
                msg.what = MSG_SHOWINFO;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFailure(String message, String for_param) {
                Toast.makeText(CardinfoActivity.this, "Fetching failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
