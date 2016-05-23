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

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.controller.UserController;
import cn.edu.bupt.citymanageapp.util.MToast;
import cn.edu.bupt.citymanageapp.util.ThreadPool;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class ModifyPasswordActivity extends Activity implements View.OnClickListener {

    private final int UPDATE_PASSWORD_OK = 0x100;

    private final int UPDATE_PASSWORD_FAIL = 0x101;

    private String userName;


    private Button btnBack;

    private EditText passwordEdit;

    private EditText confirmPasswordEdit;

    private Button btnCommit;

    private ProgressBar progressBar;


    private Context context = ModifyPasswordActivity.this;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case UPDATE_PASSWORD_OK:
                    gotoLoginPage();
                    break;
                case UPDATE_PASSWORD_FAIL:
                    progressBar.setVisibility(View.GONE);
                    MToast.show(context, "密码修改失败");
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
        setContentView(R.layout.modify_password_activity);

        initDataByIntent();
        initView();
        bindEvents();
    }

    private void initDataByIntent() {
        Intent intent = getIntent();
        if (null != intent && intent.hasExtra("USER_NAME")) {
            userName = intent.getStringExtra("USER_NAME");
        }
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btnBack);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        confirmPasswordEdit = (EditText) findViewById(R.id.confirmPasswordEdit);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void bindEvents() {
        btnBack.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnCommit:
                hideInputMethod();
                validateInputPassword();
                break;
            default:
                break;
        }
    }

    private void validateInputPassword() {
        String password = passwordEdit.getText().toString().trim();
        String confirmPassword = confirmPasswordEdit.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            MToast.show(this, "请输入新密码");
        } else if (TextUtils.isEmpty(confirmPassword)) {
            MToast.show(this, "请再输入一次新密码");
        } else if (!password.equals(confirmPassword)) {
            MToast.show(this, "两次密码输入不一致");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            updatePasswordToDB(password);
        }
    }

    private void updatePasswordToDB(final String password) {
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                boolean updateOK = UserController.getInstance(context).updatePassword(userName, password);

                if (updateOK) {
                    handler.sendEmptyMessage(UPDATE_PASSWORD_OK);
                } else {
                    handler.sendEmptyMessage(UPDATE_PASSWORD_FAIL);
                }
            }
        });
    }

    private void gotoLoginPage() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void hideInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onDestroy() {
        progressBar.setVisibility(View.GONE);

        super.onDestroy();
    }
}
