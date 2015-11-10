package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.gof.scut.androidcourse.BitmapUtil;
import com.gof.scut.androidcourse.R;
import com.google.zxing.WriterException;

/**
 * Created by Administrator on 2015/11/8.
 */
public class CreateQRcodeActivity extends Activity {

    ImageView mIvqrcode;
    boolean needIni;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqrcode);
        mIvqrcode =(ImageView)findViewById(R.id.iv_qrcode);
        needIni=true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(needIni&&hasFocus){
            int width =mIvqrcode.getWidth();
            try {
                Bitmap bm= BitmapUtil.createQRCode(getData(), width);
                mIvqrcode.setImageBitmap(bm);
                needIni=false;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private String getData(){
        if(getIntent().getBundleExtra("data")!=null){
            int index =getIntent().getBundleExtra("data").getInt("index");
            return ("androidcourse://"+index);
        }else{
            return ("androidcourse://"+0);
        }
    }

}
