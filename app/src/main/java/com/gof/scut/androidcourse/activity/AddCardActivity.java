package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;

public class AddCardActivity extends Activity {

    Button mBtnensure;
    EditText mEtname;
    EditText mEtphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mBtnensure =(Button)findViewById(R.id.btn_commit);
        mEtname=(EditText)findViewById(R.id.et_name);
        mEtphone=(EditText)findViewById(R.id.et_phone);
        iniListener();
    }

    protected void iniListener(){
        mBtnensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEtphone.getText().toString())||TextUtils.isEmpty(mEtname.getText().toString())){
                    Toast.makeText(AddCardActivity.this,"input error",Toast.LENGTH_SHORT).show();
                    return ;
                }
                Card2.createLocalCard(mEtname.getText().toString(),mEtphone.getText().toString())
                        .commitLocal(AddCardActivity.this);
            }
        });
    }

}
