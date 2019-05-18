package android.my.garage.util;

import android.os.Handler;

import java.util.HashMap;

public class ObjectBox {
    private static final ObjectBox ourInstance = new ObjectBox();
    private JDBC pointerJDBC;
    private Handler mHandler;
    /* 单例模式核心代码 */
    public static ObjectBox getInstance() {
        return ourInstance;
    }

    public JDBC getPointerJDBC() {
        return pointerJDBC;
    }

    public void setPointerJDBC(JDBC pointerJDBC) {
        this.pointerJDBC = pointerJDBC;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    /* 构造方法 因为是单例模式，所以是private属性 */
    private ObjectBox() {
    }
}
