package android.my.garage.src;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.my.garage.R;
import android.my.garage.util.ActionMes;
import android.my.garage.util.ObjectBox;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.Socket;
import java.util.HashMap;

public class HomeFragment extends Fragment implements View.OnClickListener{
    final String TAG = "HomeFragment";
    MainActivity fatherActivity;
    Handler mHandler;       //myhander还没初始
    Socket toServer;
    Button firstBtn;
    Button secondBtn;
    Button thirdBtn;
    Button fourBtn;
    Button fiveBtn;
    Button sixBtn;
    Button sevenBtn;
    Button eightBtn;
    EditText garageNum;
    ObjectBox oBox = ObjectBox.getInstance();
    HashMap<Integer,Integer> garageID_Map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        return view;
    }
    @SuppressLint("all")
    public void initView(View view){
        firstBtn = view.findViewById(R.id.firstBtn);
        secondBtn = view.findViewById(R.id.secondBtn);
        thirdBtn = view.findViewById(R.id.thirdBtn);
        fourBtn = view.findViewById(R.id.fourBtn);
        fiveBtn = view.findViewById(R.id.fiveBtn);
        sixBtn = view.findViewById(R.id.sixBtn);
        sevenBtn = view.findViewById(R.id.sevenBtn);
        eightBtn = view.findViewById(R.id.eightBtn);
        garageNum = view.findViewById(R.id.garageNum);
        garageID_Map = new HashMap<>();
        garageID_Map.put(R.id.firstBtn,1);
        garageID_Map.put(R.id.secondBtn,2);
        garageID_Map.put(R.id.thirdBtn,3);
        garageID_Map.put(R.id.fourBtn,4);
        garageID_Map.put(R.id.fiveBtn,5);
        garageID_Map.put(R.id.sixBtn,6);
        garageID_Map.put(R.id.sevenBtn,7);
        garageID_Map.put(R.id.eightBtn,8);
        firstBtn.setOnClickListener(this);
        secondBtn.setOnClickListener(this);
        thirdBtn.setOnClickListener(this);
        fourBtn.setOnClickListener(this);
        fiveBtn.setOnClickListener(this);
        sixBtn.setOnClickListener(this);
        sevenBtn.setOnClickListener(this);
        eightBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /* 先判断车库号里面是否有数据 */
        if (garageNum.getText().toString().equals("") || garageNum == null){
            Toast.makeText(getContext(),"请先确认车库号！",Toast.LENGTH_LONG).show();
            return;
        }
        openAlertWindow(v.getId());
        switch (v.getId()){
            case R.id.firstBtn:
                Log.w(TAG, "onClick: "+"点击了第一个按钮");
                break;
            case R.id.accountSetting:
                Log.w(TAG, "onClick: 点击了设置密码的界面" );
                break;
        }
    }
    /* 弹出对话框决定用户的行动 */
    public void openAlertWindow(final int id){
        /* 创建弹出对话框 */
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        final int num = garageID_Map.get(id);
        final int garageNo = Integer.parseInt(garageNum.getText().toString());
        alertBuilder.setTitle("请选择您要做的操作");
        alertBuilder.setPositiveButton("取车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.w(TAG, "onClick: 有用户取车");
                Message message = new Message();
                message.what = MainActivity.CAR_GET;
                message.obj = new ActionMes(garageNo,num,"CAR_GET");
                mHandler.sendMessage(message);  //不需要多余的信息，只需要发送给Main一个标志位即可
            }
        });
        alertBuilder.setNegativeButton("存车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.w(TAG, "onClick: 有用户存车");
                Message message = new Message();
                message.what = MainActivity.CAR_SAVE;
                message.obj = new ActionMes(garageNo,num,"CAR_SAVE");
                mHandler.sendMessage(message);  //不需要多余的信息，只需要发送给Main一个标志位即可
            }
        });
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        /* 以下代码是根据传入的id来判断不同的按键进行操作 */
        switch (id){
            case R.id.firstBtn:
                break;
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof MainActivity){   //如果context类是MainActivity的一个实例
            fatherActivity = (MainActivity) context;
            /* 用这个方法也是可以的 */
//            mHandler = fatherActivity.getmHandler();
            mHandler = oBox.getmHandler();
        }
    }
}
