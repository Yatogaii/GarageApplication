package android.my.garage.src;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class CirclrView extends View {
    Context context;
    private Paint mPaint; //画笔组件
    //确定圆的半径
    private float mRadius = 100f;
    //圆的圆心的x坐标
    float pointX;
    //圆的圆心的Y坐标
    float pointY;
    public CirclrView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        /* 初始化xy坐标 */
        getCentralPosition();
    }
    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawCircle(pointX,pointY,mRadius+80,mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(pointX,pointY,mRadius,mPaint);
    }
    void getCentralPosition(){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        pointX = width/2;
        pointY = height/2-40;
    }
}
