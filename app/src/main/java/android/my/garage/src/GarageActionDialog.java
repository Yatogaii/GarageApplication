package android.my.garage.src;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.my.garage.R;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GarageActionDialog extends Dialog implements View.OnClickListener {
    private String TAG = "Dialog";
    Activity context;
    Button getCar;
    Button saveCar;
    public GarageActionDialog(@NonNull Activity context) {
        super(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstancesState){
        super.onCreate(savedInstancesState);
        /* 指定布局为xml文件 */
        setContentView(R.layout.dialog_car);
    }
    public void usingDefaultSetting(){
        // 获取窗口对象
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        // 获取屏幕宽、高用
        Display d = m.getDefaultDisplay();
        // 获取对话框当前的参数值
        try{
            WindowManager.LayoutParams p = dialogWindow.getAttributes();
            // 宽度设置为屏幕的0.8
            p.width = (int) (d.getWidth() * 0.8);
            dialogWindow.setAttributes(p);
            /* 初始化按键 */
            getCar = findViewById(R.id.getCar);
            saveCar = findViewById(R.id.saveCar);
            if (getCar == null){
                Log.w(TAG, "usingDefaultSetting: 按键是null");
                return;
            }
            getCar.setOnClickListener(this);
            saveCar.setOnClickListener(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showDialog(){
        this.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saveCar:
                Log.w(TAG, "onClick: 有人存车");
                break;
            case R.id.getCar:
                Log.w(TAG, "onClick: 有人取车");
                break;
        }
    }
}
