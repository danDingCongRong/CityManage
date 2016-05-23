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

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.model.User;
import cn.edu.bupt.citymanageapp.controller.UserController;
import cn.edu.bupt.citymanageapp.util.MToast;
import cn.edu.bupt.citymanageapp.util.PatternUtil;
import cn.edu.bupt.citymanageapp.util.ThreadPool;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private final String[] captchaArray = {"950930", "186735", "739265", "923627", "379451", "941734",};

    private final int QUERY_USER_OK = 0x100;

    private final int QUERY_USER_FAIL = 0x101;

    private final int INSERT_USER_OK = 0x200;

    private final int INSERT_USER_FAIL = 0x201;

    private final int COUNTDOWN = 0x301;

    private int captchaIndex = 0;

    private String userName;

    private String password;

    private String captcha;


    private Button btnBack;

    private EditText userNameEdit;

    private EditText passwordEdit;

    private EditText captchaEdit;

    private TextView captchaImage;

    private Button btnSendCaptcha;

    private Button btnCommit;

    private ProgressBar progressBar;

    private Timer timer;

    private Context context = RegisterActivity.this;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case QUERY_USER_OK:
                    progressBar.setVisibility(View.GONE);
                    MToast.show(context, "用户名已存在");
                    break;
                case QUERY_USER_FAIL:
                    updateUserInfoToDB();
                    break;
                case INSERT_USER_OK:
                    progressBar.setVisibility(View.GONE);
                    gotoLoginPage();
                    break;
                case INSERT_USER_FAIL:
                    progressBar.setVisibility(View.GONE);
                    MToast.show(context, "注册用户失败");
                    break;
                case COUNTDOWN:
                    updateCountdown(msg.arg1);
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
        setContentView(R.layout.register_activity);

        initView();
        bindEvents();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btnBack);
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        captchaEdit = (EditText) findViewById(R.id.captchaEdit);
        captchaImage = (TextView) findViewById(R.id.captchaImage);
        btnSendCaptcha = (Button) findViewById(R.id.btnSendCaptcha);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void bindEvents() {
        btnBack.setOnClickListener(this);
        btnSendCaptcha.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnSendCaptcha:
                validatePhoneNumber();
                break;
            case R.id.btnCommit:
                hideInputMethod();
                validateRegisterInfo();
                break;
            default:
                break;
        }
    }

    private void validatePhoneNumber() {
        userName = userNameEdit.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            MToast.show(this, "请输入手机号");
        } else if (!PatternUtil.isMobile(userName)) {
            MToast.show(this, "手机号格式不对");
        } else {
            simulateSendCaptcha(userName);
        }
    }

    private void validateRegisterInfo() {
        userName = userNameEdit.getText().toString().trim();
        password = passwordEdit.getText().toString().trim();
        captcha = captchaEdit.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            MToast.show(this, "请输入手机号");
        } else if (!PatternUtil.isMobile(userName)) {
            MToast.show(this, "手机号格式不对");
        } else if (TextUtils.isEmpty(password)) {
            MToast.show(this, "请输入密码");
        } else if (!PatternUtil.isPassword(password)) {
            MToast.show(this, "密码格式不对");
        } else if (TextUtils.isEmpty(captcha)) {
            MToast.show(this, "请输入验证码");
        } else if (!captchaArray[captchaIndex].equals(captcha)) {
            MToast.show(context, "验证码不正确");
        } else {
            progressBar.setVisibility(View.VISIBLE);
            checkContainsUserName();
        }
    }

//    private void sendCaptcha(String phoneNumber) {
//        Uri uri = Uri.parse("smsto:" + phoneNumber);
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        intent.putExtra("sms_body", CAPTCHA);
//        startActivity(intent);
//    }

    private void simulateSendCaptcha(String phoneNumber) {
        captchaIndex = (captchaIndex + 1) % captchaArray.length;
        captchaImage.setText(captchaArray[captchaIndex]);

        startCountdown();
    }

    private void checkContainsUserName() {
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                boolean containsUserName = UserController.getInstance(context).containsUserName(userName);

                Message msg = handler.obtainMessage();
                if (containsUserName) {
                    msg.what = QUERY_USER_OK;
                } else {
                    msg.what = QUERY_USER_FAIL;
                }
                handler.sendMessage(msg);
            }
        });
    }

    private void updateUserInfoToDB() {
        ThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                User user = new User(userName, password);
                boolean updateOK = UserController.getInstance(context).insertUser(user);
                if (updateOK) {
                    handler.sendEmptyMessage(INSERT_USER_OK);
                } else {
                    handler.sendEmptyMessage(INSERT_USER_FAIL);
                }
            }
        });
    }

    private void gotoLoginPage() {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
    }

    private void startCountdown() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            private int countdown = 60;

            @Override
            public void run() {
                Message msg = handler.obtainMessage();
                msg.what = COUNTDOWN;
                msg.arg1 = countdown--;
                handler.sendMessage(msg);
            }
        }, 0, 1000);
    }

    private void updateCountdown(int countdown) {
        if (countdown == 57) {
            captchaImage.setVisibility(View.VISIBLE);
        }

        if (countdown > 0 && countdown <= 60) {
            btnSendCaptcha.setFocusable(false);
            btnSendCaptcha.setText(countdown + "后重新发送");
        } else {
            cancelCountdown();
            btnSendCaptcha.setFocusable(true);
            btnSendCaptcha.setText("发送验证码");
        }
    }

    private void cancelCountdown() {
        if (null != timer) {
            timer.cancel();
        }
    }

    private void hideInputMethod() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
    }

    @Override
    protected void onDestroy() {
        hideInputMethod();
        cancelCountdown();

        super.onDestroy();
    }
}
