package android.my.garage.src;

import android.content.Intent;
import android.my.garage.R;
import android.my.garage.util.JDBC;
import android.my.garage.util.ObjectBox;
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
*
* 5..6差不多搞一搞handler和jdbc传输数据类的使用了
* 写给5.7 已经用单例模式存放好handler和jdbc了，但不知道能不能用，明天试试！
* 5.8 还差服务器端和单片机 还有app和服务器的信息完善 还有app界面完善
* */
public class MainActivity extends AppCompatActivity {
    public static final int CAR_GET = 100;
    public static final int ACTION_LOGIN = -100;
    public static final int CAR_SAVE = 123;

    FrameLayout FrameContainer;
    Fragment homeFragment;
    Fragment settingFragment;
    Fragment currentFragment;
    FragmentManager fragManager;
    FragmentTransaction fragTransaction;        //以上均为Fragment的管理控件

    BottomNavigationView bottomBar;

    ObjectBox oBox;
    Handler mHandler;                           //传递消息用的句柄
    JDBC conn2Ser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        oBox = ObjectBox.getInstance();
        setContentView(R.layout.activity_main);
        createHandler();
        initInterface();
    }
    private void createHandler(){
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                super.handleMessage(msg);
                switch (msg.what){
                    case MainActivity.CAR_GET:
                        conn2Ser.carAction(MainActivity.CAR_GET);
                        Log.w("handler", "handleMessage: "+conn2Ser.toString() );
                        break;
                    case MainActivity.CAR_SAVE:
                        conn2Ser.carAction(MainActivity.CAR_SAVE);
                        Log.w("handler", "handleMessage: "+conn2Ser.toString() );
                        break;
                }
            }
        };
        oBox.setmHandler(mHandler);
        conn2Ser = oBox.getPointerJDBC();
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
