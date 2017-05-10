package yangzhidan.imageeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 2017/4/18.
 */

public class CustomSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable, Handler.Callback {
    // SurfaceHolder
    private SurfaceHolder mSurfaceHolder;

    private Context context;

    private int viewWidth;
    private int viewHeight;

    private boolean startDraw;
    //半径
    private int radius;
    // Path
    private Path mPath = new Path();
    // 画笔
    private Paint mpaint = new Paint();

    private Canvas canvas;
    //滑板背景（保存绘制的图片）
    private Bitmap saveBitmap;
    //图像
    Bitmap bitmap;
    //
    private SurfaceListener surfaceListener;

    private String inputString="";

    private String urlPath;
    private List<Path> pathList = new ArrayList<>();
    private List<DrawPath> drawPathList = new ArrayList<>();


    /**
     * 0矩形
     * 1圆
     * 2箭头
     * 3铅笔
     * 4文字
     * 5撤回
     */
    private static int state = 0;

    public CustomSurfaceView(Context context, String url, boolean s) {
        this(context, null);
        this.urlPath = url;
        saveBitmap = Bitmap.createBitmap(720, 1000, Bitmap.Config.ARGB_8888);
        this.context = context;

    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(); // 初始化

    }

    private void initView() {
        setMeasuredDimension(720, 1000);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
//        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);

    }

    private Handler handler = new Handler(this);

    @Override
    public void run() {
        while (startDraw) {
//            draw();
//            urlPath ="http://www.tederen.com:8017/upload/5278/4f039f79f2f0c604a87fa4b244d7a875";
            if (urlPath != null)
                bitmap = BitmapUtils.getBitmap(urlPath);
            handler.sendEmptyMessage(1);
        }
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
        if (urlPath != null) {
            new Thread(this).start();

        }
    }

    /*
         * 创建
         */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startDraw = true;

        canvas = mSurfaceHolder.lockCanvas();
//        canvas.drawColor(Color.WHITE);
        canvas.setBitmap(saveBitmap);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        new Thread(this).start();
//        bitmap= BitmapUtils.getBitmap(url);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getWidth();
        viewHeight = getHeight();

    }

    /*
         *
         */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    /*
     * 销毁
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        startDraw = false;
    }

    int startX;
    int startY;
    int stopX;
    int stopY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath = new Path();
                mpaint = new Paint();
                startX = 0;
                startY = 0;
                startX = (int) event.getX();
                startY = (int) event.getY();
                mPath.moveTo(startX, startY);
                inputString ="";

                break;
            case MotionEvent.ACTION_MOVE:
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                if (state == 0) {
                    draws();

                } else if (state == 3) {

                    draws();

                } else if (state == 1) {
                    double d = Math.abs(stopX - startX) * Math.abs(stopX - startX) + Math.abs(stopY - startY) * Math.abs(stopY - startY);
                    radius = (int) Math.sqrt(d);
                    draws();
                } else if (state == 2) {
                    draws();

                } else if (state == 4) {
                    draws();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (state == 0) {
//                    rectBeanList.add(new RectBean(startX, startY, stopX, stopY));
//                    pointBeanList.add(new PointBean(startX, startY, stopX, stopY, 0));

                    mPath.moveTo(startX, startY);
                    mPath.lineTo(startX, stopY);
                    mPath.lineTo(stopX, stopY);
                    mPath.lineTo(stopX, startY);
                    mPath.lineTo(startX, startY);
                    mPath.close();
//                    pathList.add(mPath);
//                    drawPathList.add(new DrawPath(mpaint,mPath));

                } else if (state == 2) {
                    mPath.moveTo(stopX, stopY);
                    mPath.lineTo(x3, y3);
                    mPath.lineTo(x4, y4);
                    mPath.close();
                    canvas.drawPath(mPath, mpaint);

                    mPath.moveTo(startX, startY);
                    mPath.lineTo(x23, y23);
                    mPath.lineTo(x24, y24);
                    mPath.close();
                } else if (state == 1) {

                    mPath.addCircle(startX, startY, radius, Path.Direction.CW);
                } else if (state == 4) {
//                    mPath.moveTo(startX, startY);
//                    mPath.lineTo(startX, stopY);
//                    mPath.lineTo(stopX, stopY);
//                    mPath.lineTo(stopX, startY);
//                    mPath.lineTo(startX, startY);
//                    mPath.close();

                    if (surfaceListener != null && Math.abs(startX - stopX)> 100)
                    {

                        surfaceListener.startDrawText();
                    }

                }

                if (state != 5)
                    drawPathList.add(new DrawPath(mpaint, mPath));
                break;
        }
        return true;
    }
    private boolean input;
    public void inputText(String s){
        input =true;
        inputString = s;

        draws();
    }


    public void reset() {
        mPath.reset();
    }
    private List<TextBean> tests = new ArrayList<>();

    public void setState(int state) {
        this.state = state;
        if (state != 4 && input);
        {
            input = false;
            tests.add(new TextBean(startX,stopY,inputString));
        }
    }

    public void draws() {
        if (bitmap == null) {
            Toast.makeText(getContext(), "加载图片失败", Toast.LENGTH_SHORT).show();
            Log.e("msg", "加载图片失败");
            return;
        }

//            mpaint.setStrokeWidth(DensityUtil.px2dip(getContstopXt(), 30));
        canvas = mSurfaceHolder.lockCanvas();
//        canvas.setBitmap(saveBitmap);
        Rect rectF = new Rect(0, 0, getWidth(), getHeight());   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
        canvas.drawBitmap(bitmap, null, rectF, null);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setAntiAlias(true);
        for (int i = 0; i < drawPathList.size(); i++) {
            //把path中的路线绘制出来
            canvas.drawPath(drawPathList.get(i).path, drawPathList.get(i).paint);
        }

        for (int i = 0; i < tests.size(); i++) {
            mpaint.setColor(Color.RED);
            mpaint.setTextSize(DisplayUtil.dip2px(context,10));
            canvas.drawText(tests.get(i).text,tests.get(i).x-10, tests.get(i).y-10,mpaint);

        }
        mpaint.setColor(Color.RED);
        if (state == 0) {
            mpaint.setColor(Color.RED);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeWidth(DisplayUtil.dip2px(context,3));
            canvas.drawRect(startX, startY, stopX, stopY, mpaint);

        } else if (state == 3) {

            mpaint.setStrokeWidth(DisplayUtil.dip2px(context,3));
            mpaint.setStyle(Paint.Style.STROKE);
            mPath.lineTo(stopX, stopY);
            //把path中的路线绘制出来
            canvas.drawPath(mPath, mpaint);

        } else if (state == 1) {
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setStrokeWidth(5);
            canvas.drawCircle(startX, startY, radius, mpaint);


        } else if (state == 2) {

            jiantou(startX, startY, stopX, stopY);

        } else if (state == 4) {

            PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
            mpaint.setPathEffect(effects);
            mpaint.setStyle(Paint.Style.STROKE);
            mpaint.setColor(Color.GRAY);
            mpaint.setStrokeWidth(DisplayUtil.dip2px(context,3));
            canvas.drawRect(startX, startY, stopX, stopY, mpaint);
            mpaint.setStyle(Paint.Style.FILL);
            mpaint.setTextSize(DisplayUtil.dip2px(context,25));

            Rect targetRect = new Rect(startX, startY, stopX, stopY);
//            canvas.drawRect(targetRect, mpaint);
            mpaint.setColor(Color.RED);
            Paint.FontMetricsInt fontMetrics = mpaint.getFontMetricsInt();
            // 转载请注明出处：http://blog.csdn.net/hursing
            int baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
            mpaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(inputString, targetRect.centerX(), baseline, mpaint);

        }

        mSurfaceHolder.unlockCanvasAndPost(canvas);

    }

    int x3 = 0;
    int y3 = 0;
    int x4 = 0;
    int y4 = 0;

    int x23 = 0;
    int y23 = 0;
    int x24 = 0;
    int y24 = 0;

    public void jiantou(int startX, int startY, int stopX, int stopY) {
        mpaint.setStyle(Paint.Style.FILL);
        mpaint.setStrokeWidth(1);
        double d = Math.abs(stopX - startX) * Math.abs(stopX - startX) + Math.abs(stopY - startY) * Math.abs(stopY - startY);
        int r = (int) Math.sqrt(d);//两点之间的距离
        double H = r / 5; // 箭头的高度
        double L = r / 15; // 底边的一半

        x3 = 0;
        y3 = 0;
        x4 = 0;
        y4 = 0;

        x23 = 0;
        y23 = 0;
        x24 = 0;
        y24 = 0;

        double awrad = Math.atan(L / H); // 箭头角度
        double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
        double[] arrXY_1 = rotateVec(stopX - startX, stopY - startY, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(stopX - startX, stopY - startY, -awrad, true, arraow_len);
        //add
        double H2 = r / 6;
        double L2 = r / 30;
        double awrad2 = Math.atan(L2 / H2); // 箭头角度
        double arraow_len2 = Math.sqrt(L2 * L2 + H2 * H2); // 箭头的长度
        double[] arrXY2_1 = rotateVec(stopX - startX, stopY - startY, awrad2, true, arraow_len2);
        double[] arrXY2_2 = rotateVec(stopX - startX, stopY - startY, -awrad2, true, arraow_len2);


        double x_3 = stopX - arrXY_1[0]; // (x3,y3)第一个端点
        double y_3 = stopY - arrXY_1[1];
        double x_4 = stopX - arrXY_2[0]; // (x4,y4)第二个端点
        double y_4 = stopY - arrXY_2[1];

        double x2_3 = stopX - arrXY2_1[0]; // (x3,y3)箭头尾巴的第一个端点
        double y2_3 = stopY - arrXY2_1[1];
        double x2_4 = stopX - arrXY2_2[0]; // (x4,y4)箭头尾巴的第二个端点
        double y2_4 = stopY - arrXY2_2[1];


        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();

        //add
        Double X23 = new Double(x2_3);
        x23 = X23.intValue();
        Double Y23 = new Double(y2_3);
        y23 = Y23.intValue();
        Double X24 = new Double(x2_4);
        x24 = X24.intValue();
        Double Y24 = new Double(y2_4);
        y24 = Y24.intValue();


        Path triangle = new Path();
        triangle.moveTo(stopX, stopY);
        triangle.lineTo(x3, y3);
        triangle.lineTo(x4, y4);
        triangle.close();
        canvas.drawPath(triangle, mpaint);


        Path triangle2 = new Path();
        triangle2.moveTo(startX, startY);
        triangle2.lineTo(x23, y23);
        triangle2.lineTo(x24, y24);
        triangle2.close();
        canvas.drawPath(triangle2, mpaint);

    }


    /**
     * 计算三角形的其他两个点
     *
     * @param px
     * @param py
     * @param ang
     * @param isChLen
     * @param newLen
     * @return
     */
    public double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {
        double mathstr[] = new double[2];
        //矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }

    @Override
    public boolean handleMessage(Message msg) {
        canvas = mSurfaceHolder.lockCanvas();
//        canvas.drawColor(Color.WHITE);
//        canvas.setBitmap(saveBitmap);
        //这里相当于是一个预览图
        Rect rectF = new Rect(0, 0, viewWidth, viewHeight);   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
        if (bitmap!= null&& canvas!= null)
        canvas.drawBitmap(bitmap, null, rectF, null);
        if (canvas!= null)
        mSurfaceHolder.unlockCanvasAndPost(canvas);
        startDraw = false;

        return false;
    }


    public class DrawPath {

        public Paint paint;
        public Path path;

        public DrawPath(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;
        }
    }

    public class TextBean{

        public int x;
        public int y;
        public String text;

        public TextBean(int x, int y, String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }
    }

    public Bitmap getBitmap() {
        canvas = getHolder().lockCanvas();
        canvas.setBitmap(saveBitmap);
        Rect rectF = new Rect(0, 0, getWidth(), getHeight());   //w和h分别是屏幕的宽和高，也就是你想让图片显示的宽和高
        canvas.drawBitmap(bitmap, null, rectF, null);
        for (int i = 0; i < drawPathList.size(); i++) {
            //把path中的路线绘制出来
            canvas.drawPath(drawPathList.get(i).path, drawPathList.get(i).paint);
        }

        for (int i = 0; i < tests.size(); i++) {
            mpaint.setColor(Color.RED);
            mpaint.setTextSize(DisplayUtil.dip2px(context,25));
            canvas.drawText(tests.get(i).text,tests.get(i).x-10, tests.get(i).y-10,mpaint);

        }
        mSurfaceHolder.unlockCanvasAndPost(canvas);

        return saveBitmap;
    }

    public void revocation() {
        if (drawPathList.size() > 0) {
            drawPathList.remove(drawPathList.size() - 1);
            state = 5;
            draws();
        }
    }

    public void setSurfaceListener(SurfaceListener surfaceListener) {
        this.surfaceListener = surfaceListener;
    }

    public interface SurfaceListener{

        void startDrawText();
    }
}
