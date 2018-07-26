package com.example.wuchangi.registerandlogindemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView backImageView;
    private EditText phoneNumEditText, smsCodeEditText, passwordEditText;
    private TextView termsForUsageTextView;
    private Button registerButton, getSmsCodeButton;

    //输入法管理器
    private InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);

        //初始化界面
        initView();
    }

    //初始化界面
    private void initView()
    {
        backImageView = (ImageView) findViewById(R.id.iv_back);
        backImageView.setOnClickListener(this);

        phoneNumEditText = (EditText) findViewById(R.id.et_register_phonenum);
        phoneNumEditText.setOnClickListener(this);
        smsCodeEditText = (EditText) findViewById(R.id.et_register_sms_code);
        smsCodeEditText.setOnClickListener(this);
        passwordEditText = (EditText) findViewById(R.id.et_register_password);
        passwordEditText.setOnClickListener(this);


        termsForUsageTextView = (TextView) findViewById(R.id.tv_register_terms_for_usage);
        termsForUsageTextView.setOnClickListener(this);

        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(this);
        getSmsCodeButton = (Button) findViewById(R.id.btn_get_sms_code);
        getSmsCodeButton.setOnClickListener(this);

        //初始化输入法
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    //设置各个控件的监听事件
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //返回登录界面
            case R.id.iv_back:
                finish();
                break;

            //点击手机号码输入框
            case R.id.et_register_phonenum:
                makeEditTextGetFocus(phoneNumEditText);
                break;

            //点击短信验证码输入框
            case R.id.et_register_sms_code:
                makeEditTextGetFocus(smsCodeEditText);
                break;

            //点击密码输入框
            case R.id.et_register_password:
                makeEditTextGetFocus(passwordEditText);
                break;

            //使用条款
            case R.id.tv_register_terms_for_usage:
                Toast.makeText(this, "打开使用条款中...", Toast.LENGTH_SHORT).show();
                break;

            //注册
            case R.id.btn_register:
                register();
                break;

            //获取短信验证码
            case R.id.btn_get_sms_code:

                String phoneNum = phoneNumEditText.getText().toString().trim();

                //若手机号码非法
                if (phoneNum.length() != 11)
                {
                    Toast.makeText(this, "手机号码有误！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "获取验证码中...", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    //注册操作
    private void register()
    {
        //让所有输入框获取不到焦点，并退出输入法
        makeAllEditTextsNoFocus();

        String phoneNum = phoneNumEditText.getText().toString().trim();
        String smsCode = smsCodeEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //若手机号码为空
        if (TextUtils.isEmpty(phoneNum))
        {
            Toast.makeText(this, "手机号码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        //若手机号码非法
        if (phoneNum.length() != 11)
        {
            Toast.makeText(this, "手机号码有误！", Toast.LENGTH_SHORT).show();
            return;
        }

        //若短信验证码为空
        if (TextUtils.isEmpty(smsCode))
        {
            Toast.makeText(this, "验证码不能为空！", Toast.LENGTH_SHORT).show();
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
        progressDialog.setMessage("正在注册...");
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

                //返回登录界面
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
        smsCodeEditText.setFocusable(false);
        passwordEditText.setFocusable(false);

        if (inputMethodManager.isActive())
        {
            inputMethodManager.hideSoftInputFromWindow(phoneNumEditText.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(smsCodeEditText.getWindowToken(), 0);
            inputMethodManager.hideSoftInputFromWindow(passwordEditText.getWindowToken(), 0);
        }
    }


}
