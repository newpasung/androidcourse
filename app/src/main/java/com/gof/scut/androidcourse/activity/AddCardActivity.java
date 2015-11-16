package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class AddCardActivity extends Activity {

    Button mBtnensure;
    EditText mEtname;
    EditText mEtphone;
    EditText mEtcompany;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mBtnensure = (Button) findViewById(R.id.btn_commit);
        mEtname = (EditText) findViewById(R.id.et_name);
        mEtphone = (EditText) findViewById(R.id.et_phone);
        mEtcompany = (EditText) findViewById(R.id.et_company);
        iniListener();
    }

    protected void iniListener() {
        mBtnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEtphone.getText().toString()) || TextUtils.isEmpty(mEtname.getText().toString())) {
                    Toast.makeText(AddCardActivity.this, "至少输入名字和手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog = ProgressDialog.show(AddCardActivity.this, "", "waiting", true, false);
                addLocalCard();
            }
        });
    }

    private void addLocalCard() {
        RequestParams params = new RequestParams();
        params.put("name", mEtname.getText().toString());
        params.put("company", mEtcompany.getText().toString());
        params.put("phone", mEtphone.getText().toString());
        params.put("color", 1);
        HttpClient.post(this, "card/addlocalcard", params, new JsonResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    JSONObject carddata = data.getJSONObject("card");
                    Card2.insertOrUpdate(carddata);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                finish();
            }

            @Override
            public void onFailure(String message, String for_param) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(AddCardActivity.this, "Failed to add card", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
