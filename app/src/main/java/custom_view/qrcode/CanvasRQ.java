package custom_view.qrcode;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ysy15350.readpacket.R;

import java.util.Hashtable;

public class CanvasRQ extends View {
	private Bitmap mBitmap;
	// private Bitmap mBG;
	private Bitmap mOverBp;
	public static String qr = "https://www.longkin.net/"; // 不能为中文
	public static int QR_WIDTH = 400;
	public static int QR_HEIGHT = 400;

	private float iconWidth;
	private float iconHeight;
	private float qrCodeWidth;
	private float qrCodeHeight;
	private String url = "http://www.yfxgame.com";

	private Paint paint = null;
	// 前景色
	int FOREGROUND_COLOR = 0xff000000;
	// 背景色
	int BACKGROUND_COLOR = 0xffffffff;
	private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
	// 图片宽度的一般
	public static int IMAGE_HALFWIDTH = 40;

	// 插入到二维码里面的图片对象
	private Bitmap mIcon;
	// 需要插图图片的大小 这里设定为40*40
	int[] mPixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];

	public CanvasRQ(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		paint = new Paint();
		// mBG=BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.ic_text_item_bg_default);
		mOverBp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
		try {
			CanvasRQ.qr = url;
			cretaeBitmap(qr, mOverBp);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CanvasRQ(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QrCode);

		iconWidth = typedArray.getDimension(R.styleable.QrCode_iconWidth, 0);
		iconHeight = typedArray.getDimension(R.styleable.QrCode_iconHeight, 0);
		qrCodeWidth = typedArray.getDimension(R.styleable.QrCode_qrCodeWidth, 0);
		qrCodeHeight = typedArray.getDimension(R.styleable.QrCode_qrCodeHeight, 0);
		qr = typedArray.getString(R.styleable.QrCode_url);

		QR_WIDTH = (int) qrCodeWidth;
		QR_HEIGHT = (int) qrCodeWidth;

		IMAGE_HALFWIDTH = (int) iconWidth;

		paint = new Paint();
		// createImage();
		// mBG=BitmapFactory.decodeResource(context.getResources(),
		// R.drawable.ic_text_item_bg_default);
		mOverBp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
	}

	public CanvasRQ(Context context) {
		super(context);
		paint = new Paint();
		mOverBp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			cretaeBitmap(qr, mOverBp);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 定义圆角矩形对象
		int left = (getWidth() - mBitmap.getWidth()) / 2;
		int top = (getHeight() - mBitmap.getHeight()) / 2;
		RectF rectF1 = new RectF(left - 10, top - 10, left + mBitmap.getWidth() + 10, top + mBitmap.getHeight() + 10);
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		canvas.drawRoundRect(rectF1, 20, 20, paint);
		canvas.drawBitmap(mBitmap, left, top, null);
	}

	public void setUrl(String url) {
		qr = url;
		postInvalidate();
	}

	private void createImage() {
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();
			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(qr, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(qr, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}

			mBitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			mBitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成二维码 中间插入小图片
	 * 
	 * @param str
	 *            内容
	 * @return Bitmap
	 * @throws WriterException
	 */
	public Bitmap cretaeBitmap(String str, Bitmap icon) throws WriterException {
		// 缩放一个40*40的图片
		icon = Untilly.zoomBitmap(icon, IMAGE_HALFWIDTH);
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH && y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = icon.getPixel(x - halfW + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = FOREGROUND_COLOR;
					} else { // 无信息设置像素点为白色
						pixels[y * width + x] = BACKGROUND_COLOR;
					}
				}

			}
		}
		mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		mBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return mBitmap;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public String getContent() {
		return qr;
	}
}
