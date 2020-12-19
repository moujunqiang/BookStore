package bjfu.it.lixin.bookstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 密码登录
     */
    private RadioButton mRbLoginPwd;
    /**
     * 验证码登录
     */
    private RadioButton mRbLoginCode;
    private RadioGroup mRgLogin;
    private Spinner mSpLogin;
    private EditText mEtPhone;
    private EditText mEtPassword;
    /**
     * 忘记密码
     */
    private Button mBtnForget;
    /**
     * 记住密码
     */
    private CheckBox mCbForget;
    /**
     * 登录
     */
    private Button mBtnCommit;
    private String code = "code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mRbLoginPwd = (RadioButton) findViewById(R.id.rb_login_pwd);
        mRbLoginCode = (RadioButton) findViewById(R.id.rb_login_code);
        mRgLogin = (RadioGroup) findViewById(R.id.rg_login);
        mSpLogin = (Spinner) findViewById(R.id.sp_login);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mBtnForget = (Button) findViewById(R.id.btn_forget);
        mBtnForget.setOnClickListener(this);
        mCbForget = (CheckBox) findViewById(R.id.cb_forget);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);
        mRgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_login_code:
                        mBtnForget.setText("获取验证码");
                        break;
                    case R.id.rb_login_pwd:
                        mBtnForget.setText("忘记密码");


                        break;
                }
            }
        });
        mSpLogin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mBtnCommit.setText("登录");
                    mBtnForget.setText("获取验证码");
                } else {
                    mBtnCommit.setText("注册");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_forget:
                if (mBtnForget.getText().toString().equals("忘记密码")) {
                    if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                        Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                    intent.putExtra("phone", mEtPhone.getText().toString());
                    startActivityForResult(intent, 100);
                } else {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(LoginActivity.this);
                    normalDialog.setTitle("请记住验证码");
                    final int i = (int) ((Math.random() * 9 + 1) * 100000);
                    normalDialog.setMessage("手机号" + getIntent().getStringExtra("phone") + ",本次验证码是" + i + "，请输入验证码");
                    normalDialog.setPositiveButton("好的",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    code = i + "";
                                }
                            });

                    normalDialog.show();
                }
                break;
            case R.id.btn_commit:
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(LoginActivity.this);
                normalDialog.setTitle("登录成功");
                final int i = (int) ((Math.random() * 9 + 1) * 100000);
                normalDialog.setMessage("您的手机号码是" + mEtPhone.getText().toString() + "，类型是个人用户，恭喜你通过登录验证，点击确定返回上个页面");
                normalDialog.setPositiveButton("确定返回",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                code = i + "";
                            }
                        });
                normalDialog.setNegativeButton("我再看看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                normalDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            mEtPassword.setText("");

        }
    }
}