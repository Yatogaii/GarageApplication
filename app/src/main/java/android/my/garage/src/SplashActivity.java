package android.my.garage.src;

import android.content.Intent;
import android.my.garage.R;
import android.my.garage.src.MainActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle bundle) {    //OnCreate函数，打开App时执行的操作。
        super.onCreate(bundle);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_loading);
        new Thread() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),LoginActivity.class);       //这两句设置界面间的跳转
                try {
                    //延迟的时间，这里设置为10秒
                    long count_Down = 3000;
                    Thread.sleep(count_Down);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                startActivity(intent);                              //开始跳转;
            }
        }.start();
    }
}
