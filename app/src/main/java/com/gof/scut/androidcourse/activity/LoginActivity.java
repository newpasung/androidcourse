package com.gof.scut.androidcourse.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.gof.scut.androidcourse.LocalBrCast;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.fragment.FragmentTransactionExtended;
import com.gof.scut.androidcourse.fragment.LoginFragment;
import com.gof.scut.androidcourse.fragment.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    private Fragment curFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private BroadcastReceiver showRegisterBroadcastReceiver;
    private BroadcastReceiver showLoginBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        initBroadcastReceiver();
    }

    private void initUI() {
        loginFragment = (LoginFragment) getFragmentManager().findFragmentById(R.id.login_content);
        curFragment = loginFragment;
    }

    private void initBroadcastReceiver() {
        showRegisterBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (registerFragment == null) {
                    registerFragment = new RegisterFragment();
                }
                replaceFragment(registerFragment);
            }
        };

        showLoginBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (loginFragment == null) {
                    loginFragment = new LoginFragment();
                }
                replaceFragment(loginFragment);
            }
        };

        LocalBrCast.register(this, "show register fragment", showRegisterBroadcastReceiver);
        LocalBrCast.register(this, "show login fragment", showLoginBroadcastReceiver);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended
                = new FragmentTransactionExtended(this, fragmentTransaction, curFragment, fragment, R.id.login_content);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.SLIDE_HORIZONTAL);
        fragmentTransactionExtended.commit(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBrCast.unregisterReceiver(this, showRegisterBroadcastReceiver);
        LocalBrCast.unregisterReceiver(this, showLoginBroadcastReceiver);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
