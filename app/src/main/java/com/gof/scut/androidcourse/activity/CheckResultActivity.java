package com.gof.scut.androidcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gof.scut.androidcourse.BitmapUtil;
import com.gof.scut.androidcourse.R;
import com.google.zxing.WriterException;

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
        if (resultCode == RESULT_OK&&requestCode == REQUESTCODE_SCAN_PIC) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            //应用的规则为前缀加上 index
            if(scanResult.startsWith("androidcourse://")){
                String index_str=scanResult.replace("androidcourse://","");
                Intent intent =new Intent();
                Bundle bundle1 =new Bundle();
                bundle1.putInt("index",Integer.valueOf(index_str));
                intent.putExtra("data", bundle1);
                intent.setClass(CheckResultActivity.this,CardinfoActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                mTvdata.setText("此二维码不能被本应用使用");
            }
        }
    }
}
