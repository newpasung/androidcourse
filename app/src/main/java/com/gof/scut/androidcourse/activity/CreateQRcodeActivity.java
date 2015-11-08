package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gof.scut.androidcourse.R;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CreateQRcodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqrcode);
        //TODO 在BItmapUtil里面有生成二维码的api
    }
}
