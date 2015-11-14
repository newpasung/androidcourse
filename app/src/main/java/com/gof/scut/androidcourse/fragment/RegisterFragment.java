package com.gof.scut.androidcourse.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gof.scut.androidcourse.LocalBrCast;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.XRotationAnimation;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.gof.scut.androidcourse.net.RequestParamName;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

public class RegisterFragment extends Fragment {

    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    private Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initListener();
    }

    private void initUI(View view) {

        usernameInputLayout = (TextInputLayout) view.findViewById(R.id.username);

        passwordInputLayout = (TextInputLayout) view.findViewById(R.id.password);

        registerBtn = (Button) view.findViewById(R.id.btn_register);
    }

    private void initListener() {
        final EditText usernameEditText = usernameInputLayout.getEditText();
        final EditText passwordEditText = passwordInputLayout.getEditText();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (username.length() == 0) {
                    usernameInputLayout.setErrorEnabled(true);
                    usernameInputLayout.setError("请输入用户名");
                } else if (password.length() == 0) {
                    passwordInputLayout.setErrorEnabled(true);
                    passwordInputLayout.setError("请输入密码");
                } else {
                    RequestParams params = new RequestParams();
                    params.put(RequestParamName.USERNAME, username);
                    params.put(RequestParamName.PASSWORD, password);
                    HttpClient.post(getActivity(), "user/register", params, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            XRotationAnimation animation = new XRotationAnimation();
                            animation.setRepeatMode(Animation.INFINITE);
                            registerBtn.startAnimation(animation);
                            Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
                            LocalBrCast.sendBroadcast(getActivity(), "show login fragment");
                        }

                        @Override
                        public void onFailure(String message, String for_param) {
                            if (for_param.equals(RequestParamName.USERNAME)) {
                                usernameInputLayout.setErrorEnabled(true);
                                usernameInputLayout.setError(message);
                            } else if (for_param.equals(RequestParamName.PASSWORD)) {
                                passwordInputLayout.setErrorEnabled(true);
                                passwordInputLayout.setError(message);
                            } else {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            }

                            Log.i("login", message + "  " + for_param);
                        }

                    });
                }
            }
        });
    }
}
