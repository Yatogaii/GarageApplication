package android.my.garage.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class JDBC implements Serializable {
    private final String TAG = "服务器链接";
    private final int ERROR = 0;
    private final int RIGHT = 1;
    private final int REGSITER = -1;
    private Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    public JDBC(){
        try{
            socket = new Socket("112.74.163.49",8080);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            Log.w(TAG, "JDBC: 数据库链接完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int checkAccount(String account, String password){
        JSONObject jsonObject = new JSONObject();
        int res;
        try{
            jsonObject.put("action",100);
            jsonObject.put("account",account);
            jsonObject.put("password",password);
            String json = jsonObject.toString();
            dos.write(json.getBytes());
            byte[] bytes = new byte[1024]; //会报错java.lang.NumberFormatException: Invalid int: "12�����������������������������������
            int length = dis.read(bytes);
            String str = new String(bytes,0,length);
            res = Integer.parseInt(str);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -100;
    }

    public Socket getSocket(){
        return socket;
    }

}
