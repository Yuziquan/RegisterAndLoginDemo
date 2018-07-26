package com.example.wuchangi.registerandlogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView registerTextView, termsForUsageTextView;
    private EditText phoneNumEditText, passwordEditText;
    private Button loginButton, forgetPasswordButton;
    private ImageView qqLoginImageView, wechatLoginImageView, weiboLoginImageView;

    //输入法管理器
    private InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);

        //初始化界面
        initView();

    }

    //初始化界面
    private void initView()
    {
        registerTextView = (TextView) findViewById(R.id.tv_register);
        registerTextView.setOnClickListener(this);
        termsForUsageTextView = (TextView) findViewById(R.id.tv_login_terms_for_usage);
        termsForUsageTextView.setOnClickListener(this);

        phoneNumEditText = (EditText) findViewById(R.id.et_login_phonenum);
        phoneNumEditText.setOnClickListener(this);
        passwordEditText = (EditText) findViewById(R.id.et_login_password);
        passwordEditText.setOnClickListener(this);


        loginButton = (Button) findViewById(R.id.btn_login);
        loginButton.setOnClickListener(this);
        forgetPasswordButton = (Button) findViewById(R.id.btn_forget_password);
        forgetPasswordButton.setOnClickListener(this);

        qqLoginImageView = (ImageView) findViewById(R.id.iv_qq_login);
        qqLoginImageView.setOnClickListener(this);
        wechatLoginImageView = (ImageView) findViewById(R.id.iv_wechat_login);
        wechatLoginImageView.setOnClickListener(this);
        weiboLoginImageView = (ImageView) findViewById(R.id.iv_weibo_login);
        weiboLoginImageView.setOnClickListener(this);

        //初始化输入法
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    //设置各个控件的监听事件
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //注册
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            //使用条款
            case R.id.tv_login_terms_for_usage:
                Toast.makeText(this, "打开使用条款中...", Toast.LENGTH_SHORT).show();
                break;

            //点击手机号码输入框
            case R.id.et_login_phonenum:
                makeEditTextGetFocus(phoneNumEditText);
                break;

            //点击密码输入框
            case R.id.et_login_password:
                makeEditTextGetFocus(passwordEditText);
                break;

            //登录
            case R.id.btn_login:
                login();
                break;

            //忘记密码
            case R.id.btn_forget_password:
                Toast.makeText(this, "找回密码中...", Toast.LENGTH_SHORT).show();
                break;

            //qq登录
            case R.id.iv_qq_login:
                Toast.makeText(this, "qq登录中...", Toast.LENGTH_SHORT).show();
                break;

            //微信登录
            case R.id.iv_wechat_login:
                Toast.makeText(this, "微信登录中...", Toast.LENGTH_SHORT).show();
                break;

            //微博登录
            case R.id.iv_weibo_login:
                Toast.makeText(this, "微博登录中...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //登录操作
    private void login()
    {
        //让所有输入框获取不到焦点，并退出输入法
        makeAllEditTextsNoFocus();

        String phoneNum = phoneNumEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //若手机号码为空
        if (TextUtils.isEmpty(phoneNum))
        {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        //若手机号码非法
        if(phoneNum.length() != 11)
        {
            Toast.makeText(this, "手机号码有误！", Toast.LENGTH_SHORT).show();
            return;
        }

        //若密码为空
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }


        //显示等待条
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在登录...");
        progressDialog.show();

        //模拟后台请求
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                //销毁等待条
                progressDialog.dismiss();


                //跳转到主界面
                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                //销毁当前activity
                finish();

            }
        }).start();

    }

    //当EditText被点击时获取焦点，并显示输入法
    private void makeEditTextGetFocus(EditText editText)
    {
        //设置输入框被点击时获取焦点
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        editText.findFocus();

        //显示输入法
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    //使界面所有EditText获取不到焦点，并隐藏输入法
    private void makeAllEditTextsNoFocus()
    {
        phoneNumEditText.setFocusable(false);
        passwordEditText.setFocusable(false);

        if(inputMethodManager.isActive())
        {
            inputMethodManager.hideSoftInputFromWindow(phoneNumEditText.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
        }
    }

}
