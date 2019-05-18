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
    byte[] recBuf;
    public JDBC(){
        try{
            getConnect();
            recBuf = new byte[1024];
            Log.w(TAG, "JDBC: 数据库链接完成");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int checkAccount(String account, String password){
        getConnect();
        JSONObject jsonObject = new JSONObject();
        int res;
        try{
            jsonObject.put("action",-100);
            jsonObject.put("account",account);
            jsonObject.put("password",password);
            String json = jsonObject.toString();
            Log.w(TAG, "checkAccount: "+json );
            dos.write(json.getBytes());
            int length = dis.read(recBuf);
            String str = new String(recBuf,0,length);
            res = Integer.parseInt(str);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -100;
    }

    public void carAction(final ActionMes action){
        getConnect();
        new Thread(){
            @Override
            public void run(){
                try{
                    JSONObject jsonObject = new JSONObject();
                    int actionCode = action.getAction().equals("CAR_GET") ? 100 : 123;
                    String msg = ""+action.getGarageID() + (actionCode == 100 ? 'p' : 't')+ action.getNum();
                    jsonObject.put("action",actionCode);
                    jsonObject.put("message",msg);
                    String json = jsonObject.toString();
                    dos.write(json.getBytes());
                    Log.w(TAG, "carAction: 发送成功"+json);
                    int len = dis.read(recBuf);
                    int res = Integer.parseInt(new String(recBuf,0,len));
                }catch (Exception e){e.printStackTrace();}
            }
        }.start();
    }
    private void getConnect(){
        try{
        if (socket == null || !socket.isConnected()){
            socket = new Socket("112.74.163.49",8080);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Socket getSocket(){
        return socket;
    }

}
