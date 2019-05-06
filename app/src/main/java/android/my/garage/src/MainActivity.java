package android.my.garage.src;

import android.content.Intent;
import android.my.garage.R;
import android.my.garage.util.JDBC;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
/*
* 4.25
* 完成了安卓和服务器的链接，但是服务器那边和数据库还有和单片机的通讯没有完成
* app这边还差界面和设置界面没有完成
* 4.27
* 正调试服务器端数据库，出了点bug，先去吃饭了 _mysql_connector.MySQLInterfaceError: Commands out of sync; you can't run this command now
* 数据库bug修复，去掉commit即可，commit并没有实际作用
* 5.2正式完成登录界面，着手准备主界面的ui设计
* */
public class MainActivity extends AppCompatActivity {
    public static int CAR_GET = 100;
    public static int CAR_SAVE = 123;

    FrameLayout FrameContainer;
    Fragment homeFragment;
    Fragment settingFragment;
    Fragment currentFragment;
    FragmentManager fragManager;
    FragmentTransaction fragTransaction;        //以上均为Fragment的管理控件

    Handler mHandler;                           //传递消息用的句柄
    BottomNavigationView bottomBar;
    JDBC conn2Ser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        conn2Ser = (JDBC)getIntent().getSerializableExtra("Server");
        createHandler();
        initInterface();
    }
    private void createHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        break;
                }
            }
        };
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void initInterface(){
        bottomBar = findViewById(R.id.navigation_bar);
        fragManager = getSupportFragmentManager();
        FrameContainer = findViewById(R.id.container_frag);
        homeFragment = new HomeFragment();
        settingFragment = new SettingFragment();
        /* 传输数据用的bundle */
        fragTransaction = fragManager.beginTransaction();
        fragTransaction.add(R.id.container_frag,homeFragment).hide(homeFragment);
        fragTransaction.add(R.id.container_frag,settingFragment).hide(settingFragment);
        fragTransaction.show(homeFragment);
        currentFragment = homeFragment;
        fragTransaction.commit();
        //添加监听器
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragTransaction = fragManager.beginTransaction();   //每次触发事件都需要再次commit一次，防止报Already commit错误
                switch (menuItem.getItemId()){
                    case R.id.menu_home: {
                        if(homeFragment.isHidden()){
                            fragTransaction.hide(currentFragment);
                            fragTransaction.show(homeFragment);
                            currentFragment = homeFragment;
                            fragTransaction.commit();
                        }
//                        bottomBar.setSelectedItemId(R.id.menu_home);
                        Log.w("Navigation", "onNavigationItemSelected: "+R.id.menu_home);
                        return true;
                    }
                    case R.id.menu_setting:{
                        if(settingFragment.isHidden()) {
                            fragTransaction.hide(currentFragment);
                            fragTransaction.show(settingFragment);
                            currentFragment = settingFragment;
                            fragTransaction.commit();
                        }
//                        bottomBar.setSelectedItemId(R.id.menu_setting);
                        Log.w("Navigation", "onNavigationItemSelected: "+R.id.menu_setting);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
