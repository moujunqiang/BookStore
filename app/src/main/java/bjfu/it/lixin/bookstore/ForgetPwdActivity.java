package bjfu.it.lixin.bookstore;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtPassword1;
    private EditText mEtPassword2;
    private EditText mEtCode;
    /**
     * 获取验证码
     */
    private Button mBtnGetCode;
    /**
     * 确定
     */
    private Button mBtnCommit;
    private String code = "code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        initView();
    }

    private void initView() {
        mEtPassword1 = (EditText) findViewById(R.id.et_password1);
        mEtPassword2 = (EditText) findViewById(R.id.et_password2);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mBtnGetCode = (Button) findViewById(R.id.btn_get_code);
        mBtnGetCode.setOnClickListener(this);
        mBtnCommit = (Button) findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_get_code:
                /* @setIcon 设置对话框图标
                 * @setTitle 设置对话框标题
                 * @setMessage 设置对话框消息提示
                 * setXXX方法返回Dialog对象，因此可以链式设置属性
                 */
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(ForgetPwdActivity.this);
                normalDialog.setTitle("请记住验证码");
                final int i = (int) ((Math.random() * 9 + 1) * 100000);
                normalDialog.setMessage("手机号" + getIntent().getStringExtra("phone") + ",本次验证码是" + i + "，请输入验证码");
                normalDialog.setPositiveButton("好的",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                code=i+"";
                            }
                        });

                normalDialog.show();
                break;
            case R.id.btn_commit:
                if (TextUtils.isEmpty(mEtPassword1.getText().toString())) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEtPassword1.getText().toString())) {
                    Toast.makeText(this, "请确认新密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mEtPassword1.getText().toString().equals(mEtPassword2.getText().toString())) {
                    Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mEtCode.getText().toString())) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!code.equals(mEtCode.getText().toString())) {
                    Toast.makeText(this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                setResult(100);
                finish();
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}