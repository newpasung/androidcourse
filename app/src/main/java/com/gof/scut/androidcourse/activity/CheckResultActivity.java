package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gof.scut.androidcourse.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CheckResultActivity extends Activity {

    TextView mTvdata;
    final int REQUESTCODE_SCAN_PIC=0;
    boolean shouldRedirection ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkresult);
        mTvdata=(TextView)findViewById(R.id.tv_text);
        shouldRedirection=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(shouldRedirection){
            Intent intent =new Intent();
            intent.setClass(this, CaptureActivity.class);
            startActivityForResult(intent,REQUESTCODE_SCAN_PIC);
            shouldRedirection=false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //下面是取回来的二维码信息
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            mTvdata.setText(scanResult);
        }
    }
}
