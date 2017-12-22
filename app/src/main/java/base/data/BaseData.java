package base.data;

import android.content.Context;
import android.util.Log;

import java.io.ObjectStreamException;

import base.model.UserInfo;
import common.CommFunAndroid;
import common.cache.ACache;
import common.string.JsonConvertor;

public class BaseData {


    private static Context mContext;

    private static ACache aCache;


    /**
     * 是否登录
     */
    private boolean isLogin = false;

    String token;

    /**
     * 是否有网络
     */
    public static boolean isNetwork;

    private final static String TAG = "BaseData";

    private BaseData() {
    }

    public static BaseData getInstance() {
        return BaseDataHolder.sInstance;
    }

    public static BaseData getInstance(Context context) {
        init(context);
        return BaseDataHolder.sInstance;
    }

    private static class BaseDataHolder {
        private static final BaseData sInstance = new BaseData();
    }

    // 杜绝单例对象在反序列化时重新生成对象
    private Object readResolve() throws ObjectStreamException {
        return BaseDataHolder.sInstance;
    }

    private static void init(Context context) {
        mContext = context;
        if (aCache == null && context != null) {
            aCache = ACache.get(context);
        }
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        if (mContext != null) {
            return CommFunAndroid.getAppVersionCode(mContext);
        }
        return -1;
    }


    /**
     * 获取当前用户登录信息
     *
     * @return
     */
    public static UserInfo getUserInfo() {

        try {
            UserInfo userInfo = UserHelper.getInstance().getLastLoginUserInfo();
            if (userInfo != null)
                Log.d(TAG, "getUserInfo() called userInfo=" + JsonConvertor.toJson(userInfo));
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static int getUid() {
        try {
            UserInfo userInfo = UserHelper.getInstance().getLastLoginUserInfo();
            if (userInfo != null) {
                return userInfo.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void setToken(String token) {

        setCache("token", token);
    }

    public static String getToken() {
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            return userInfo.getToken();
        }
        return getCache("token");
    }

    /**
     * 缓存已登录用户信息
     *
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo) {

        try {
            if (userInfo != null)
                UserHelper.getInstance().userLogin(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注销
     */
    public void loginOut() {
        try {
            UserInfo userInfo = getUserInfo();
            if (userInfo != null) {
                userInfo.setToken("");
                setUserInfo(userInfo);
            }
            UserHelper.getInstance().loginOut();////把所有用户登录状态改为未登录
            setToken("");//清空token
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value) {
        int uid = getUid();
        if (aCache != null && value != null) {
            Log.d(TAG, "setCache() called with: key = [" + key + uid + "], value = [" + value + "]");
            aCache.put(key + uid, value);
        }
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void setCache(String key, String value, int time) {
        if (aCache != null && value != null) {
            int uid = getUid();
            Log.d(TAG, "setCache() called with: key = [" + key + uid + "], value = [" + value + "], time = [" + time + "]");
            aCache.put(key + uid, value, time);
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static String getCache(String key) {
        if (aCache != null) {
            int uid = getUid();
            String cacheStr = aCache.getAsString(key + uid);
            Log.d(TAG, "getCache() called with: key = [" + key + "],value=" + cacheStr);
            return cacheStr;
        }
        return "";
    }

}