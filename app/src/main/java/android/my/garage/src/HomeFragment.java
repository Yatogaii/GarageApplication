package android.my.garage.src;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.my.garage.R;
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

import java.net.Socket;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initView(view);
        return view;
    }

    public void initView(View view){
        firstBtn = view.findViewById(R.id.firstBtn);
        secondBtn = view.findViewById(R.id.secondBtn);
        thirdBtn = view.findViewById(R.id.thirdBtn);
        fourBtn = view.findViewById(R.id.fourBtn);
        fiveBtn = view.findViewById(R.id.fiveBtn);
        sixBtn = view.findViewById(R.id.sixBtn);
        sevenBtn = view.findViewById(R.id.sevenBtn);
        eightBtn = view.findViewById(R.id.eightBtn);
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
        openAlertWindow(v.getId());
        switch (v.getId()){
            case R.id.firstBtn:
                Log.w(TAG, "onClick: "+"点击了第一个按钮");
                break;
        }
    }
    /* 弹出对话框决定用户的行动 */
    public void openAlertWindow(int id){
        /* 创建弹出对话框 */
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("请选择您要做的操作");
        alertBuilder.setPositiveButton("取车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.w(TAG, "onClick: 有用户取车");
                Message message = new Message();
                message.what = MainActivity.CAR_GET;
                mHandler.sendMessage(message);  //不需要多余的信息，只需要发送给Main一个标志位即可
            }
        });
        alertBuilder.setNegativeButton("存车", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.w(TAG, "onClick: 有用户存车");
                Message message = new Message();
                message.what = MainActivity.CAR_SAVE;
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
            mHandler = fatherActivity.getmHandler();
        }
    }
}
