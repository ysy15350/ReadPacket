package common;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ExitApplication extends Application {

    private static final String TAG = "ExitApplication";

    private List<Activity> activityList = new LinkedList();
    private static ExitApplication instance;

    /**
     * 单例模式中获取唯一的ExitApplication实例
     *
     * @return 返回单例ExitApplication
     */
    public static ExitApplication getInstance() {
        if (null == instance) {
            instance = new ExitApplication();
        }
        return instance;
    }


    /**
     * 遍历所有Activity并finish
     */
    public void exit() {

        System.exit(0);
        int myPid = android.os.Process.myPid();
        android.os.Process.killProcess(myPid);
    }


}