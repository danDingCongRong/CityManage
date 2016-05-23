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
import android.widget.ImageView;
import android.widget.ProgressBar;

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.controller.UserController;
import cn.edu.bupt.citymanageapp.util.MToast;
import cn.edu.bupt.citymanageapp.util.PatternUtil;
import cn.edu.bupt.citymanageapp.util.ThreadPool;

/**
 * Created by chenjun14 on 16/5/19.
 */
public class FindPasswordActivity extends Activity implements View.OnClickListener {

    private final int QUERY_USER_OK = 0x100;

    private final int QUERY_USER_FAIL = 0x101;

    private int captchaIndex = 0;

    private final int[] captchaId = {R.drawable.captcha_1, R.drawable.captcha_2,
            R.drawable.captcha_3, R.drawable.captcha_4,};
    private final String[] captchaArray = {"xf3x", "79vs", "nxkv", "9040"};


    private Button btnBack;

    private EditText userNameEdit;

    private EditText captchaEdit;

    private ImageView captchaImage;

    private ImageView btnRefresh;

    private Button btnCommit;

    private ProgressBar progressBar;


    private Context context = FindPasswordActivity.this;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case QUERY_USER_OK:
                    gotoModifyPasswordPage();
                    break;
                case QUERY_USER_FAIL:
                    progressBar.setVisibility(View.GONE);
                    MToast.show(context, "用户名不存在");
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
        setContentView(R.layout.find_password_activity);

        initView();
        bindEvents();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btnBack);
        userNameEdit = (EditText) findViewById(R.id.userNameEdit);
        captchaEdit = (EditText) findViewById(R.id.captchaEdit);
        captchaImage = (ImageView) findViewById(R.id.captchaImage);
        btnRefresh = (ImageView) findViewById(R.id.btnRefresh);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    private void bindEvents() {
        btnBack.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
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
                validateInputInfo();
                break;
            case R.id.btnRefresh:
                updateCaptcha();
                break;
            default:
                break;
        }
    }

    private void validateInputInfo() {
        String phoneNumber = userNameEdit.getText().toString().trim();
        String captcha = captchaEdit.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) {
            MToast.show(context, "请输入用户名");
        } else if (!PatternUtil.isMobile(phoneNumber)) {
            MToast.show(context, "手机号格式不正确");
        } else if (TextUtils.isEmpty(captcha)) {
            MToast.show(context, "请输入验证码");
        } else if (!captcha.equals(captchaArray[captchaIndex])) {
            MToast.show(context, "验证码不正确");
        } else {
            checkContainsUserName(phoneNumber);
        }
    }

    private void updateCaptcha() {
        captchaIndex = (captchaIndex + 1) % captchaArray.length;
        captchaImage.setImageResource(captchaId[captchaIndex]);
    }

    private void checkContainsUserName(final String userName) {
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


    private void gotoModifyPasswordPage() {
        String userName = userNameEdit.getText().toString().trim();
        Intent intent = new Intent(context, ModifyPasswordActivity.class);
        intent.putExtra("USER_NAME", userName);
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
        progressBar.setVisibility(View.GONE);

        super.onDestroy();
    }

}
