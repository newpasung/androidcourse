package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CheckResultActivity extends Activity {

    TextView mTvdata;
    final int REQUESTCODE_SCAN_PIC = 0;
    Button mBtnadd;
    long cardid;
    long cardUid;
    ProgressDialog progressDialog;
    boolean shouldRedirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkresult);
        mTvdata = (TextView) findViewById(R.id.tv_text);
        mBtnadd = (Button) findViewById(R.id.btn_add);
        shouldRedirection = true;
        mBtnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard();
            }
        });
    }

    private void addCard() {
        progressDialog = ProgressDialog.show(this, "", "waiting", true, false);
        String url;
        if (cardUid == 0) {
            url = "card/addlocalcard";
        } else {
            url = "card/addusercard";
        }
        RequestParams params = new RequestParams();
        params.put("cardid", cardid);
        HttpClient.post(this, url, params, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONObject card = data.getJSONObject("card");
                    Card2.insertOrUpdate(card);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(String message, String for_param) {
                Toast.makeText(CheckResultActivity.this, "fetching failed", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (shouldRedirection) {//跳转到扫描二维码的界面
            Intent intent = new Intent();
            intent.setClass(this, CaptureActivity.class);
            startActivityForResult(intent, REQUESTCODE_SCAN_PIC);
            shouldRedirection = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUESTCODE_SCAN_PIC) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            //应用的规则为前缀加上cardid和";"和uid
            if (scanResult.startsWith("androidcourse://")) {
                String code = scanResult.replace("androidcourse://", "");
                String[] data_str = code.split(";");
                cardid = Long.valueOf(data_str[0]);
                cardUid = Long.valueOf(data_str[1]);
                if (Card2.getCard(cardid) == null) {
                    mBtnadd.setVisibility(View.VISIBLE);
                    mTvdata.setText("会添加用户名片");
                } else {
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putLong("cardid", cardid);
                    intent.putExtra("data", bundle1);
                    intent.setClass(CheckResultActivity.this, CardinfoActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                mTvdata.setText("此二维码不能被本应用使用");
            }
        }
    }
}
