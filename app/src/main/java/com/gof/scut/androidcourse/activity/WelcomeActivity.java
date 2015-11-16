package com.gof.scut.androidcourse.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.fragment.FirstWelcomeFragment;
import com.gof.scut.androidcourse.fragment.NormalWelcomeFragment;
import com.gof.scut.androidcourse.storage.XManager;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initUI();
    }

    private void initUI() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (XManager.hasOpened(WelcomeActivity.this)) {
            transaction.replace(R.id.welcome_content, new NormalWelcomeFragment());
        } else {
            transaction.replace(R.id.welcome_content, new FirstWelcomeFragment());
        }
        transaction.commit();
    }

}
