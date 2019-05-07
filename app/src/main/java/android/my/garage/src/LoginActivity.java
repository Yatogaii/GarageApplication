package android.my.garage.src;

import android.content.Intent;
import android.my.garage.R;
import android.my.garage.util.JDBC;
import android.my.garage.util.ObjectBox;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/* 5.2登录界面基本实现，等待细节优化 */
public class LoginActivity extends AppCompatActivity {
    String acc;
    String pass;
    Button loginButton;
    EditText account;
    EditText password;
    JDBC conn2Ser;
    ObjectBox oBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        oBox = ObjectBox.getInstance();
        initInterface();
    }
    private void initInterface(){
        loginButton = findViewById(R.id.login);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc = account.getText().toString();
                pass = password.getText().toString();
                Log.w("Login Message:","账号:"+acc+" 密码:"+pass);
                new Thread(){
                    @Override
                    public void run(){
                        Looper.prepare();       //子线程中显示toast的工作之一
                        final int RESULT_ERROR = 11;
                        final int RESULT_REGISTER = 12;
                        final int RESULT_SUCCESS = 10;
                        conn2Ser = new JDBC();
                        /* jdbc辅助类的传递 */
                        oBox.setPointerJDBC(conn2Ser);

                        int checkResult = conn2Ser.checkAccount(acc,pass);
                        Log.w("查询结果: ", ""+checkResult);
                        switch (checkResult){
                            case RESULT_ERROR:
                                Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                                break;
                            case RESULT_REGISTER:
                                Toast.makeText(getApplicationContext(),"已为您注册该用户",Toast.LENGTH_SHORT).show();
                                startAct();
                                break;
                            case RESULT_SUCCESS:
                                Toast.makeText(getApplicationContext(),"密码正确",Toast.LENGTH_SHORT).show();
                                startAct();
                                break;
                            default :
                                Toast.makeText(getApplicationContext(),"接收数据错误",Toast.LENGTH_SHORT).show();
                                break;
                        }
                        Looper.loop();      //子线程中显示toast的工作之二
                        startAct();
                    }
                }.start();
            }
        });
    }
    private void startAct(){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
