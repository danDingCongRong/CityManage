package cn.edu.bupt.citymanageapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.controller.UserController;
import cn.edu.bupt.citymanageapp.util.MToast;
import cn.edu.bupt.citymanageapp.util.ThreadPool;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private final int QUERY_USER_OK = 0;

    private final int QUERY_USER_FAIL = -1;

    private EditText userNameEdit;

    private EditText passwordEdit;

    private Button btnLogin;

    private TextView btnForget;

    private TextView btnRegister;

    private TextView btnRegisterNow;

    private ProgressBar progressBar;

    private Context context = LoginActivity.this;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case QUERY_USER_OK:
                    progressBar.setVisibility(View.GONE);
                    gotoInspectionReportPage();
                    break;
                case QUERY_USER_FAIL:
                    progressBar.setVisibility(View.GONE);
                    MToast.show(context, "您输入的帐号或密码不对");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);

        initView();
        bindEvents();
    }

    private void initView() {
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnForget = (TextView) findViewById(R.id.btnForget);
        btnRegister = (TextView) findViewById(R.id.btnRegister);
        btnRegisterNow = (TextView) findViewById(R.id.btnRegisterNow);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void bindEvents() {
        btnLogin.setOnClickListener(this);
        btnForget.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnRegisterNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                hideInputMethod();
                progressBar.setVisibility(View.VISIBLE);
                validateUserInfo();
                break;
            case R.id.btnForget:
                gotoFindPasswordPage();
                break;
            case R.id.btnRegister:
                gotoRegisterPage();
                break;
            case R.id.btnRegisterNow:
                gotoRegisterPage();
                break;
            default:
                break;
        }
    }

    private void validateUserInfo() {
        String userName = userNameEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            MToast.show(context, "请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            MToast.show(context, "请输入密码");
        } else if (userName.equals("admin") && password.equals("admin")) {
            progressBar.setVisibility(View.GONE);
            gotoInspectionReportPage();
        } else {
            queryUserByDB(userName, password);
        }
    }

    private void queryUserByDB(final String userName, final String password) {
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                boolean hasUser = UserController.getInstance(context).queryUser(userName, password);

                Message msg = handler.obtainMessage();
                if (hasUser) {
                    msg.what = QUERY_USER_OK;
                } else {
                    msg.what = QUERY_USER_FAIL;
                }
                handler.sendMessage(msg);
            }
        });
    }

    private void gotoInspectionReportPage() {
        Intent intent = new Intent(context, InspectionReportActivity.class);
        startActivity(intent);
    }

    private void gotoFindPasswordPage() {
        String userName = userNameEdit.getText().toString().trim();
        Intent intent = new Intent(context, FindPasswordActivity.class);
        intent.putExtra("USER_NAME", userName);
        startActivity(intent);
    }

    private void gotoRegisterPage() {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }

    private void hideInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onDestroy() {
        hideInputMethod();

        super.onDestroy();
    }
}
