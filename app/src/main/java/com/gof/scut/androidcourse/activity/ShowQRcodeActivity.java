package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.gof.scut.androidcourse.BitmapUtil;
import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.storage.XManager;
import com.google.zxing.WriterException;

/**
 * Created by Administrator on 2015/11/8.
 */
public class ShowQRcodeActivity extends Activity {

    ImageView mIvqrcode;
    boolean needIni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showqrcode);
        mIvqrcode = (ImageView) findViewById(R.id.iv_qrcode);
        needIni = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (needIni && hasFocus) {
            int width = mIvqrcode.getWidth();
            try {
                Bitmap bm = BitmapUtil.createQRCode(getData(), width);
                mIvqrcode.setImageBitmap(bm);
                needIni = false;
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    private String getData() {
        if (getIntent().getBundleExtra("data") != null) {
            long cardid = getIntent().getBundleExtra("data").getLong("cardid");
            long uid = Card2.getCard(cardid).getUserid();//免得在其地方更改bundle
            Log.i("getData",1+"");
            return ("androidcourse://" + cardid + ";" + uid);
        } else {
            return ("androidcourse://" + Card2.getMyCardid(ShowQRcodeActivity.this)+";"+ XManager.getUid(this));
        }
    }

}
