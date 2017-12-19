package common;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.ysy15350.readpacket.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.cache.ACache;
import common.model.AppMemoryInfo;
import common.model.LocationInfo;
import common.model.RequestPermissionType;
import common.model.ScreenInfo;
import common.model.TelephonyManagerInfo;
import common.string.JsonConvertor;
import common.string.MD5Util;

public class CommFunAndroid extends CommFun {

    private static final String TAG = "CommFunAndroid";

    public static Context mContext;


    /**
     * CommFunAndroid单例,如果不需要Context，Handler 可直接使用commFunAndroid对象
     */
    public static CommFunAndroid commFunAndroid = new CommFunAndroid();


    public static CommFunAndroid getInstance() {
        if (commFunAndroid == null) {
            commFunAndroid = new CommFunAndroid();
        }

        return commFunAndroid;
    }

    private CommFunAndroid() {

    }

    public static String getCahePath(Context context) {
        return context.getApplicationContext().getCacheDir().getPath();
    }

    /**
     * 收集设备参数信息
     *
     * @return
     */
    public static Map<String, String> getDeviceInfo() {

        Map<String, String> infos = new HashMap<String, String>();

        try {

            if (mContext != null) {

                PackageManager pm = mContext.getPackageManager();
                PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (pi != null) {
                    String versionName = pi.versionName == null ? "null" : pi.versionName;
                    String versionCode = pi.versionCode + "";
                    infos.put("versionName", versionName);
                    infos.put("versionCode", versionCode);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }

        return infos;

    }

    public static String getDeviceInfoStr() {
        String deviceInfo = "";


        try {
            Map<String, String> infos = CommFunAndroid.getDeviceInfo();

            if (infos != null) {
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<String, String> entry : infos.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    sb.append(key + "=" + value + "\n");
                }

                deviceInfo = sb.toString();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deviceInfo;
    }

    /**
     * 获取deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        String deviceId = "";

        try {
            deviceId = new DeviceUuidFactory(context).getDeviceUuid().toString().replace("-", "_");
            Log.d(TAG, "getDeviceId() called with: deviceId = [" + deviceId + "]");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    /**
     * 获取错误信息
     *
     * @return
     */
    public static String getErrorLogContent() {
        try {
            File file = new File(CommFunAndroid.getDiskCachePath() + "/aandroid_log/error");
            if (file != null && file.exists()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {

                    String content = "";

                    for (File file_item :
                            files) {
                        if (file_item != null && file_item.exists() && file_item.isFile()) {
                            //content = content + "\n------------------------------------\n" + FileUtils.readFile(file_item.getPath());

                        }
                    }

                    return content;

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    public static int getAppVersionCode(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return 0;
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode;
    }

    /**
     * 判断是否在模拟器上运行
     *
     * @return
     */
    public boolean isEmulator() {

        try {
            String buildModel = Build.MODEL.toUpperCase();// LENOVO A355E

            if (buildModel == null || "".equals(buildModel))
                return false;

            if (buildModel.contains("SDK") || buildModel.contains("CUSTOM PHONE"))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void clearLog() {

        String errorLogPath = getDiskCachePath() + "/aandroid_log/error";
        //FileUtils.deletFile(errorLogPath);//删除目录下的所有文件
    }

    /**
     * 写错误日志
     *
     * @param str
     */
    public static void writeErrorLog(String str) {
        //FileUtils.writeLog(str, "error");
    }


    /**
     * 检测系统安全
     *
     * @param isKill 如果是模拟器运行是否关闭程序
     * @return
     */
    public boolean CheckSystemSafe(boolean isKill) {
        try {

            if (isEmulator() && isKill) {
                /**
                 * 结束进程
                 */
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            String signature = "";

            PackageInfo packageInfo = getPackageInfo();

            if (packageInfo != null) {
                if (packageInfo.signatures != null && packageInfo.signatures.length > 0)
                    signature = packageInfo.signatures[0].toCharsString();
                // signature:
                // 3082024d308201b6a003020102020455ac5b33300d06092a864886f70d0101050500306b310b3009060355040613023836311230100603550408130963686f6e6771696e67311230100603550407130963686f6e6771696e673110300e060355040a13076c6f6e676b696e3110300e060355040b13076c6f6e676b696e3110300e060355040313076c6f6e676b696e301e170d3135303732303032323133395a170d3430303731333032323133395a306b310b3009060355040613023836311230100603550408130963686f6e6771696e67311230100603550407130963686f6e6771696e673110300e060355040a13076c6f6e676b696e3110300e060355040b13076c6f6e676b696e3110300e060355040313076c6f6e676b696e30819f300d06092a864886f70d010101050003818d003081890281810093c75ba4abc572a290d0a6152c81b43c3047011e1acb308c5de24e59adbcc1921d36107f1183fc7346604d2a610277edd2f5dc3bab4dcc5e7e21bc97342351e710cb3e21ab2db936106f1be72b949795cbd3d6e8a315f515cb1ff610938c3652bbd5725aadc5807b5ed081c94cae183dc349f667a8d9b136287e85a76f3ae5d30203010001300d06092a864886f70d01010505000381810063ebeda3adfed1c3859d6b3cd10714cbc8d92b6422de8104dcff1fd88ba7bdb86ed539ef25fcc403bc5218d221b15c2a360d15134702851cb8c2b7855ed820931141bfd83a6ac7dcb0f57fb971345cbc5a6def861263e1d615fda8bb33db31ba1c12c92163c7ec294346fdfcbe456ce11cfe7573753af26ac9e300e824f49bec

                if (signature != null && !"".equals(signature))
                    signature = MD5Util.GetMD5Code(signature);
            }

            HashSet<String> signatures = new HashSet<String>();

            signatures.add("5bfc80837c7f867052998bff6598a1e5");
            signatures.add("d41d8cd98f00b204e9800998ecf8427e");// 隆金宝eclipse
            signatures.add("ab567dc9dfd35d327d387fb6b7a7b038");
            signatures.add("cefe921e1cdcd9ca20a2ffe729e0732e");// mac
            signatures.add("d3985cb5169e35e30a66d29291054bd8");// 咖啡吧
            signatures.add("d5c49803fb15e7819837cd06dafee283");// moviefriend
            // 签名后

            boolean isContainSignature = signatures.contains(signature);

            return isContainSignature;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 判断程序是否在运行(未使用，可与isTopActivity方法比较)
     *
     * @param context
     * @return
     */
    public boolean isRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> list = am.getRunningTasks(100);
        for (RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(context.getPackageName())
                    && info.baseActivity.getPackageName().equals(context.getPackageName())) {
                return true;

            }
        }
        return false;
    }

    /**
     * 程序是否是激活状态（位于堆栈的顶层）
     *
     * @return
     */
    public boolean isTopActivity() {

        try {
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasksInfo = am.getRunningTasks(1);
            if (tasksInfo.size() > 0) {

                // ComponentName topActivity = tasksInfo.get(0).topActivity;
                // 应用程序位于堆栈的顶层
                if (mContext.getPackageName().equals(tasksInfo.get(0).topActivity.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取APP内存信息
     *
     * @return
     */
    public AppMemoryInfo getAppMemoryInfo() {

        AppMemoryInfo appMemoryInfo = new AppMemoryInfo();

        try {

            long maxmemory = Runtime.getRuntime().maxMemory();// 100663296

            long nativeHeapSize = Debug.getNativeHeapSize();// 本地堆大小
            long nativeHeapAllocatedSize = Debug.getNativeHeapAllocatedSize();// 本地堆分配大小

            long nativeHeapFreeSize = Debug.getNativeHeapFreeSize();// 本地堆空闲大小

            appMemoryInfo.setMaxmemory(maxmemory);
            appMemoryInfo.setNativeHeapSize(nativeHeapSize);
            appMemoryInfo.setNativeHeapAllocatedSize(nativeHeapAllocatedSize);
            appMemoryInfo.setNativeHeapFreeSize(nativeHeapFreeSize);

            ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

            MemoryInfo info = new MemoryInfo();
            if (activityManager != null)
                activityManager.getMemoryInfo(info);
            if (info != null) {
                long availMem = info.availMem >> 10;// 系统剩余内存//1905608

                boolean lowMemory = info.lowMemory;// 系统是否处于低内存运行

                long threshold = info.threshold;// 当系统剩余内存低于threshold值时就看成低内存运行

                appMemoryInfo.setAvailMem(availMem);
                appMemoryInfo.setLowMemory(lowMemory);
                appMemoryInfo.setThreshold(threshold);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return appMemoryInfo;

    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public static void showSoftInput(View view) {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideSoftInput(View view) {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
        }
    }

    /**
     * 切换键盘
     */
    public static void toggleSoftInput() {
        if (mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 获取屏幕信息
     *
     * @return
     */
    public static ScreenInfo getScreenInfo(Activity activity) {


        ScreenInfo screenInfo = new ScreenInfo();
        try {
            if (activity == null)
                return null;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            if (displayMetrics != null) {

                int width = displayMetrics.widthPixels;
                int height = displayMetrics.heightPixels;

                screenInfo.setWidth(width);
                screenInfo.setHeight(height);
            }

            Resources res = activity.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            if (dm != null)
                screenInfo.setDensity(dm.density);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return screenInfo;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Context context) {

        if (context == null)
            return 0;

        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar = context.getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

    /**
     * 填充状态栏
     */
    public static void fullScreenStatuBar(Activity activity) {
        if (activity == null)
            return;
        try {
            //代码方式填满状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
                localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
            }
        } catch (Exception ex) {

        }
    }

    /**
     * 获取手机联网信息
     *
     * @return NetworkInfo :网络信息
     */
    public NetworkInfo getConnectionType() {
        try {
            if (mContext != null) {
                ConnectivityManager connectMgr = (ConnectivityManager) mContext
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo info = connectMgr.getActiveNetworkInfo();

                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String s = e.getMessage();
        }

        return null;

    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @return
     */
    public boolean isMobileConnected() {
        if (mContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return 网络连接类型：0:mobile(手机网路);1:WIFI;
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();// 网络连接类型：0:mobile(手机网路);1:WIFI;
            }
        }
        return -1;
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getPhoneNum() {
        try {

            // 获取手机号码
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceid = tm.getDeviceId();// 获取智能设备唯一编号

            setSharedPreferences("deviceid", deviceid);

            String tel = tm.getLine1Number();// 获取本机号码
            String imei = tm.getSimSerialNumber();// 获得SIM卡的序号
            String imsi = tm.getSubscriberId();// 得到用户Id

            String ProvidersName = "";

            // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
            if (imsi != null) {
                if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                    ProvidersName = "中国移动";
                } else if (imsi.startsWith("46001")) {
                    ProvidersName = "中国联通";
                } else if (imsi.startsWith("46003")) {
                    ProvidersName = "中国电信";
                }
            }

            if (tel.contains("+86"))
                tel = tel.substring(3, tel.length()).trim();

            return tel == null ? "" : String.valueOf(tel);
        } catch (Exception e) {
            // TODO: handle exception
            return "";
        }

    }


    /**
     * 申请权限
     */
    public static void callPhoneCheckPermission(Activity context, String phone) {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE},
                        RequestPermissionType.REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                callPhone(context, phone);
            }
        } else {
            callPhone(context, phone);
        }
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        if (!isNullOrEmpty(phone)) {
            Intent mIntent = new Intent(Intent.ACTION_CALL);
            String uriString = "tel:" + phone;
            mIntent.setData(Uri.parse(uriString));
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(mIntent);
        }
    }

    /**
     * 获取手机IP地址
     *
     * @return
     */
    public String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        // if (!inetAddress.isLoopbackAddress() && inetAddress
                        // instanceof Inet6Address) {
                        return toString(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取手机信息（手机号/设备iD等）
     *
     * @return
     */
    public TelephonyManagerInfo getTelephonyManagerInfo() {

        TelephonyManagerInfo telephonyInfo = new TelephonyManagerInfo();

        try {

            // 获取手机号码
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

            if (tm != null) {

                String deviceid = tm.getDeviceId();// 获取智能设备唯一编号

                String tel = tm.getLine1Number();// 获取本机号码

                if (tel != null && tel.contains("+86"))
                    tel = tel.substring(3, tel.length()).trim();

                String simSerialNumber = tm.getSimSerialNumber();// SIM卡序号imei
                String subscriberId = tm.getSubscriberId();// 得到用户Id imsi

                String providersName = "";

                // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
                if (subscriberId != null) {
                    if (subscriberId.startsWith("46000") || subscriberId.startsWith("46002")) {
                        providersName = "中国移动";
                    } else if (subscriberId.startsWith("46001")) {
                        providersName = "中国联通";
                    } else if (subscriberId.startsWith("46003")) {
                        providersName = "中国电信";
                    }
                }

                String phoneIp = getPhoneIp();

                telephonyInfo.setLine1Number(tel);
                telephonyInfo.setPhoneIp(phoneIp);
                telephonyInfo.setDeviceid(deviceid);
                telephonyInfo.setSimSerialNumber(simSerialNumber);
                telephonyInfo.setSubscriberId(subscriberId);
                telephonyInfo.setProvidersName(providersName);

                WifiInfo wifiInfo = getWifiInfo();

                if (wifiInfo != null) {
                    telephonyInfo.setMacAddress(wifiInfo.getMacAddress());// mac地址
                }

            }
            return telephonyInfo;
        } catch (Exception e) {
        }
        return telephonyInfo;
    }

    /**
     * 获取程序信息：versionCode，versionName，packageName，signature（程序签名）
     *
     * @return PackageInfo (versionCode，versionName，packageName，signature)
     */
    public static PackageInfo getPackageInfo() {

        PackageInfo packageInfo = null;

        try {
            if (mContext != null) {
                PackageManager manager = mContext.getPackageManager();

                packageInfo = manager.getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);

                if (packageInfo != null) {

                    // int versionCode = packageInfo.versionCode;
                    //
                    // String versionName = packageInfo.versionName;
                    //
                    // String packgename = packageInfo.packageName;
                    //
                    // String signature = "";
                    //
                    // if (packageInfo.signatures != null
                    // && packageInfo.signatures.length > 0)
                    // signature = packageInfo.signatures[0].toCharsString();

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packageInfo;

    }

    public static WifiInfo getWifiInfo() {
        WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiInfo info = wifi.getConnectionInfo();

            return info;

            // if (info != null) {
            // info.getMacAddress();
            //
            // }
        }

        return null;
    }

    /**
     * 保存数据
     */
    public static void setSharedPreferences(String key, String value) {
        try {
            if (mContext != null) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("sharedPreferences",
                        Activity.MODE_PRIVATE);

                Editor editor = sharedPreferences.edit();

                // editor.putBoolean("isTrue", true);
                // editor.putString("SessionId", SystemState.SessionId);
                editor.putString(key, value);

                editor.apply();// 安全异步写入
                // editor.commit();//会阻止调用线程，
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取键值对
     *
     * @param key
     * @return username:用户名，手机号；token：登录唯一标志；isFirstRun:是否第一次运行;JSESSIONID:
     * 会话Session
     */
    public static String getSharedPreferences(String key) {
        String value = "";
        try {
            if (mContext != null) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("sharedPreferences",
                        Activity.MODE_PRIVATE);

                value = toString(sharedPreferences.getString(key, ""));

                Log.i("longkin", String.format("getSharedPreferences(key=%s,value=%s)", key, value));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return toString(value);
    }

    /**
     * 获取全部短信
     */
    public static Uri SMS_INBOX = Uri.parse("content://sms/");

    /**
     * 获取短信验证码
     *
     * @return
     */
    public String getSmsFromPhone() {
        String sms_number = "";

        //* @param patternStr 正则表达式:默认[a-zA-Z0-9]{5}

        try {
            if (mContext == null)
                return "";
            ContentResolver cr = mContext.getContentResolver();
            String[] projection = new String[]{"address", "person", "body", "date", "type"};

            // 获取十分钟之内的短信
            String where = " date > " + (System.currentTimeMillis() - 10 * 60 * 1000);
            // " address = '1066321332' AND date > "+
            // (System.currentTimeMillis()
            // - 10 * 60 * 1000);

            Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");

            if (null == cur)
                return "";
            if (cur.moveToNext()) {
                try {
                    int column_address = cur.getColumnIndex("address");
                    int column_person = cur.getColumnIndex("person");
                    int column_body = cur.getColumnIndex("body");

                    String address = column_address == -1 ? "" : cur.getString(column_address);

                    String person = column_person == -1 ? "" : cur.getString(column_person);

                    String body = column_body == -1 ? "" : cur.getString(column_body);

                    // 获取4-6位验证码
                    Pattern pattern = Pattern.compile("[0-9]{4,6}");// "[a-zA-Z0-9]{5}"
                    Matcher matcher = pattern.matcher(body);
                    if (matcher.find()) {
                        // String res = matcher.group().substring(0, 5);
                        sms_number = matcher.group().toString();
                    }
                } catch (Exception e) {
                    // TODO: ha,ndle exception
                }

            }
        } catch (Exception e) {
            e.printStackTrace();

            String msg = e.getMessage();
        }

        return sms_number;
    }

    /**
     * 判断是否有SD卡
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取cache路径
     *
     * @param context
     * @return
     */
    public static String getDiskCachePath() {
        String path = "";
        if (mContext != null) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
                path = mContext.getExternalCacheDir().getPath();//   /storage/emulated/0/Android/data/com.ysy15350.startcarunion/cache
            } else {
                path = mContext.getCacheDir().getPath();
            }
        }
        return path;
    }

    /**
     * 无数据时显示视图
     *
     * @return
     */
    public static View nodataImageView() {
        if (mContext != null) {
            Bitmap bitmap = ((BitmapDrawable) (mContext.getResources().getDrawable(R.mipmap.icon_nodata))).getBitmap();

            ImageView imageView = new ImageView(mContext);
            imageView.setImageBitmap(bitmap);

            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return imageView;
        }
        return null;
    }

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 将图片转为Base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String getPhotoString(Bitmap bitmap) {
        String photoString = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            byte[] buffer = out.toByteArray();

            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            photoString = new String(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoString;
    }

    public static LocationInfo getLocationInfo() {

        try {
            if (ACache.aCache != null) {
                String locationJson = ACache.aCache.getAsString("location");
                if (!CommFunAndroid.isNullOrEmpty(locationJson)) {
                    LocationInfo locationInfo = JsonConvertor.fromJson(locationJson, LocationInfo.class);
                    return locationInfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
        }

        return null;
    }

    // /**
    // * 设置位置信息并存入缓存
    // *
    // * @param location
    // */
    // public static void setLocationInfo(AMapLocation location) {
    // try {
    // if (location != null) {
    // LocationInfo locationInfo = new LocationInfo();
    // locationInfo.setAccuracy(location.getAccuracy());
    // locationInfo.setAdCode(location.getAdCode());
    // locationInfo.setAddress(location.getAddress());
    // locationInfo.setCity(location.getCity());
    // locationInfo.setCityCode(location.getCityCode());
    // locationInfo.setCountry(location.getCountry());
    // locationInfo.setDistrict(location.getDistrict());
    // locationInfo.setErrorCode(location.getErrorCode());
    // locationInfo.setLatitude(location.getLatitude());
    // locationInfo.setLocationType(location.getLocationType());
    // locationInfo.setLongitude(location.getLongitude());
    // locationInfo.setPoiName(location.getPoiName());
    // locationInfo.setProvider(location.getProvider());
    // locationInfo.setProvince(location.getProvince());
    //
    // if (locationInfo != null) {
    // String locationInfoJson = JsonConvertor.toJson(locationInfo);
    // if (!CommFunAndroid.isNullOrEmpty(locationInfoJson)) {
    // if (ACache.aCache != null) {
    // ACache.aCache.put("location", locationInfoJson, ACache.TIME_MINUTE * 10);
    // Log.i("location", locationInfoJson);
    // }
    // }
    // }
    //
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

}