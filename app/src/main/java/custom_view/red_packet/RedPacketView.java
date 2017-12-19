package custom_view.red_packet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ysy15350.readpacket.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import api.model.RedPacketInfo;

/**
 * Created by yangshiyou on 2017/11/17.
 */

public class RedPacketView extends SurfaceView implements DrawInterface {

    private static final String TAG = "RedPacketView";
    private Context mContext;

    private DrawHandler drawHandler;
    private int width;
    private int height;
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;
    private int viewCount = 10;

    private int status = 0;

    private ArrayList<MoveModel> moveList = new ArrayList<>();

    private List<RedPacketInfo> mRedPacketInfos = new ArrayList<>();


    public RedPacketView(Context context) {
        this(context, null);
    }

    public RedPacketView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RedPacketView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        SurfaceHolder holder = getHolder();
        setZOrderOnTop(true);//true：显示在最顶层
        //setZOrderMediaOverlay(true);//遵从view的层级关系，不盖住上面的view
        holder.setFormat(PixelFormat.TRANSLUCENT);//设置背景透明

        //mPaint = new Paint();
        //mBgBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_red_packet);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }


    public void prepare(List<RedPacketInfo> redPacketInfos) {

        Log.d(TAG, "更新红包,数量：" + redPacketInfos.size());

        mRedPacketInfos = redPacketInfos;


        DrawThread drawThread = new DrawThread();
        drawThread.start();
        drawHandler = new DrawHandler(drawThread.getLooper(), this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_redenvelope2);
        bitmapWidth = bitmap.getWidth();
        bitmapHeight = bitmap.getHeight();

    }


    /**
     * 移除已经抢过的红包
     *
     * @param redPacketInfo
     */
    public void removeGrabedRedPacket(RedPacketInfo redPacketInfo) {

        try {
            if (redPacketInfo != null) {
                if (mRedPacketInfos != null && mRedPacketInfos.size() > 0) {

                    Log.d(TAG, "removeGrabedRedPacket: 移除前红包池数量" + mRedPacketInfos.size());

                    RedPacketInfo grabedRedPacketInfo = null;

                    for (RedPacketInfo info :
                            mRedPacketInfos) {
                        if (info != null) {
                            if (info.getId() == redPacketInfo.getId()) {
                                grabedRedPacketInfo = info;
                                break;
                            }
                        }
                    }

                    if (grabedRedPacketInfo != null) {
                        mRedPacketInfos.remove(grabedRedPacketInfo);
                    }

                    Log.d(TAG, "removeGrabedRedPacket: 移除后红包池数量" + mRedPacketInfos.size());
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMoveModel(MoveModel moveModel) {
        moveList.add(moveModel);
    }

    public void start() {
        status = 1;
        moveList.clear();
        if (mRedPacketInfos != null && mRedPacketInfos.size() > 0) {
            int size = mRedPacketInfos.size();
            int count = size > viewCount ? viewCount : size;
            for (int i = 0; i < count; i++) {
                generateModel();
            }
            if (drawHandler != null)
                drawHandler.sendEmptyMessage(DrawHandler.START_DRAW_KEY);
        }

        //drawHandler.sendEmptyMessageDelayed(DrawHandler.START_DRAW_KEY, 500);
    }

    public void resume() {
        drawHandler.sendEmptyMessage(DrawHandler.START_DRAW_KEY);
    }

    public void pause() {
        if (drawHandler != null)
            drawHandler.sendEmptyMessage(DrawHandler.STOP_DRAW_KEY);
    }

    public void quit() {
        if (null != drawHandler) {
            drawHandler.removeCallbacksAndMessages(null);
            drawHandler.getLooper().quit();
        }
    }

    //private Canvas mCanvas;
    //private int mPadding;
    //private Bitmap mBgBitmap;
    //private Paint mPaint;

    //文字画笔
    private Paint mPaint;

    //文字字体大小
    private float m_fontSize;
    //文字颜色
    private int m_textColor = Color.rgb(255, 255, 255);


    long notifyTime = 0;

    @Override
    public void startDraw() {
        SurfaceHolder holder = getHolder();
        Canvas canvas = holder.lockCanvas();
        if (null == canvas) {
            return;
        }

        m_fontSize = mContext.getResources().getDimension(R.dimen.reading_default_text_size);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        mPaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        mPaint.setTextSize(m_fontSize);// 字体大小
        mPaint.setColor(m_textColor);// 字体颜色
        //mPaint.setTypeface(typeface);
        mPaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果

        //mCanvas = canvas;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        if (moveList != null) {

            if (moveList.size() < viewCount) {

                if (mRedPacketInfos.size() >= viewCount) {
                    status = 0;//状态改为0，在更新数据时，会调用start方法
                }

                Log.d(TAG, "startDraw:通知刷新数据moveList.size()=" + moveList.size() + ",viewCount=" + viewCount);


                if (mListener != null) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - notifyTime > 10 * 1000) {
                        Log.d(TAG, "startDraw:通知刷新数据");
                        notifyTime = System.currentTimeMillis();
                        mListener.notifyLoadRedPacket();//通知刷新数据
                    }
                }
            }

            for (int i = 0; i < moveList.size(); i++) {

                if (moveList.size() > i) {

                    MoveModel moveModel = moveList.get(i);


                    canvas.drawBitmap(bitmap, moveModel.x, moveModel.y, paint);


                    if (moveModel.x > width || moveModel.y > height) {
                        resetMoveModel(moveModel);
                    } else {
                        moveModel.y += moveModel.randomY;
                    }

                    if (moveModel.redPacketInfo != null) {//绘制红包金额
                        RedPacketInfo redPacketInfo = moveModel.redPacketInfo;
                        int price = redPacketInfo.getPrice();

//                mPaint.measureText
                        String text = price + "";
                        float text_width = mPaint.measureText(text);

                        float text_x = moveModel.x + (bitmapWidth / 2) - text_width / 2;
                        float text_y = moveModel.y + (bitmapHeight / 3 * 2);

                        canvas.drawText(text, text_x, text_y, mPaint);
                    }
                }

            }

        }
        holder.unlockCanvasAndPost(canvas);
        if (drawHandler != null)
            drawHandler.sendEmptyMessage(DrawHandler.START_DRAW_KEY);
        //drawHandler.sendEmptyMessageDelayed(DrawHandler.START_DRAW_KEY, 100);
    }

    @Override
    public void stopDraw() {
        if (drawHandler != null)
            drawHandler.removeMessages(DrawHandler.START_DRAW_KEY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                checkInRect((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    /**
     * 是否点击在红包区域
     *
     * @param x
     * @param y
     */
    private void checkInRect(int x, int y) {
        int length = moveList.size();
        for (int i = 0; i < length; i++) {
            MoveModel moveModel = moveList.get(i);
            Rect rect = new Rect((int) moveModel.x, (int) moveModel.y, (int) moveModel.x + bitmapWidth, (int) moveModel.y + bitmapHeight);
            if (rect.contains(x, y)) {

                if (mListener != null) {
                    mListener.redPacketClick(moveModel);
                }

                resetMoveModel(moveModel);

                break;
            }
        }
    }

    private void resetMoveModel(MoveModel moveModel) {
        Random random = new Random();
        moveModel.redPacketInfo = getRandomRedPacketInfo();
        moveModel.x = random.nextInt(11) * (width / 10);
        moveModel.y = 0;
        moveModel.randomY = (random.nextInt(5) + 2) * getResources().getDisplayMetrics().density * 0.8f;

        if (moveModel.redPacketInfo == null)
            moveList.remove(moveModel);

    }

    Random random = new Random();

    private void generateModel() {
        MoveModel moveModel = new MoveModel();
        moveModel.moveId = moveList.size() + 1;
        moveModel.redPacketInfo = getRandomRedPacketInfo();
        moveModel.x = random.nextInt(11) * (width / 10);
        moveModel.y = 0;
        moveModel.randomY = (random.nextInt(5) + 2) * getResources().getDisplayMetrics().density * 0.8f;
        if (moveModel.redPacketInfo != null)
            moveList.add(moveModel);
    }

    int viewIndex = 0;

    /**
     * 随机获取红包
     *
     * @return
     */
    private RedPacketInfo getRandomRedPacketInfo() {

        try {

            if (mRedPacketInfos != null && mRedPacketInfos.size() > 0) {
                int size = mRedPacketInfos.size();
                int index = random.nextInt(size);

                if (size >= viewCount) {
                    if (size > viewIndex)
                        index = viewIndex++;
                    else {
                        index = random.nextInt(size);
                    }
                }
                Log.d(TAG, "随机获取红包 size=" + size + ",index=" + index);

                RedPacketInfo redPacketInfo = mRedPacketInfos.get(index);


                return redPacketInfo;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private Set<Integer> getRandomNum(int num, int count) {
        Random r = new Random();
        Set<Integer> hs = new HashSet<Integer>();
        while (hs.size() < count) {
            hs.add(r.nextInt(num));
        }

        return hs;
    }

    public int getStatus() {
        return this.status;
    }

    private Listener mListener;

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public interface Listener {
        public void redPacketClick(MoveModel moveModel);//红包点击事件

        public void notifyLoadRedPacket();//通知刷新数据
    }

}
