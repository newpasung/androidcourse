package com.gof.scut.androidcourse.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gof.scut.androidcourse.Card2;
import com.gof.scut.androidcourse.LocalBrCast;
import com.gof.scut.androidcourse.R;
import com.gof.scut.androidcourse.activity.MainActivity;
import com.gof.scut.androidcourse.net.HttpClient;
import com.gof.scut.androidcourse.net.JsonResponseHandler;
import com.gof.scut.androidcourse.net.RequestParamName;
import com.gof.scut.androidcourse.storage.XManager;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment {

    private TextInputLayout usernameInputLayout;
    private TextInputLayout passwordInputLayout;
    private Button loginBtn;
    private Button registerBtn;
    //用于控制登陆按钮动画
    private boolean shouldTrans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        initListener();
    }

    private void initListener() {
        final EditText usernameEditText = usernameInputLayout.getEditText();
        final EditText passwordEditText = passwordInputLayout.getEditText();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
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
                    HttpClient.post(getActivity(), "user/login", params, new JsonResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            try {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject userdata = data.getJSONObject("user");
                                JSONObject carddata = userdata.getJSONObject("card");
                                Card2.insertOrUpdate(carddata);
                                XManager.setLoginStatus(getActivity(), true);
                                XManager.setToken(getActivity(), response.getJSONObject("data").getJSONObject("user").getString("token"));
                                XManager.setUid(getActivity(), response.getJSONObject("data").getJSONObject("user").getInt("uid"));
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                                if (message != null) {
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            v.setEnabled(true);
                        }

                    });
                }
            }
        });

        //跳转到注册界面
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBrCast.sendBroadcast(getActivity(), "show register fragment");
            }
        });
    }

    private void initUI(View view) {
        usernameInputLayout = (TextInputLayout) view.findViewById(R.id.username);

        passwordInputLayout = (TextInputLayout) view.findViewById(R.id.password);

        loginBtn = (Button) view.findViewById(R.id.btn_login);

        registerBtn = (Button) view.findViewById(R.id.btn_register);
    }
}
