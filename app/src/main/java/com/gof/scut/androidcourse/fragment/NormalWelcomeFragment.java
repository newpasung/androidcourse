package com.gof.scut.androidcourse.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.activity.LoginActivity;
import com.gof.scut.androidcourse.activity.MainActivity;
import com.gof.scut.androidcourse.storage.XManager;

import java.util.Timer;
import java.util.TimerTask;

public class NormalWelcomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_normal_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final boolean isLogined = XManager.isLogined(getActivity());
        //延时2秒后进入登录界面或主页（取决于是否已经登录）
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), isLogined ? MainActivity.class : LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }, 0);

    }
}
