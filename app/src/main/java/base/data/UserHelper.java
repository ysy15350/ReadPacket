package base.data;

import android.util.Log;

import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;

import java.util.Collection;
import java.util.List;

import base.model.UserInfo;
import common.CommFun;
import common.database.DbUtilsXutils3;
import common.string.JsonConvertor;

/**
 * Created by yangshiyou on 2017/9/13.
 */

public class UserHelper extends DbUtilsXutils3<UserInfo> {

    private static UserHelper userHelper;

    private UserHelper() {

    }

    private static final String TAG = "UserHelper";


    public static UserHelper getInstance() {
        if (userHelper == null) {
            userHelper = new UserHelper();
            init();
        }
        return userHelper;
    }

    /**
     * 用户登录成功记录登录状态
     *
     * @param userInfo
     * @throws Exception
     */
    public void userLogin(UserInfo userInfo) throws Exception {

        WhereBuilder whereBuilder = WhereBuilder.b();
        db.update(UserInfo.class, whereBuilder, new KeyValue("isLogin", 0));//把所有用户登录状态改为未登录
        UserInfo userInfo_1 = getUserInfoByMobile(userInfo.getMobile());


        if (userInfo_1 != null) {
            userInfo.setUid(userInfo_1.getUid());
        }

        userInfo.setLoginTimeStr(CommFun.getDateString("yyyy-MM-dd HH:mm:ss"));

        saveOrUpdate(userInfo);//保存

    }

    /**
     * 获取当前登录用户
     *
     * @return
     * @throws Exception
     */
    public UserInfo getLastLoginUserInfo() throws Exception {
        List<UserInfo> list = getList();
        if (list != null) {
            Log.d(TAG, "-----------------user--begin-------------------------------------- ");
            Log.d(TAG, "listSize= " + list.size());
            for (UserInfo user :
                    list) {

                Log.d(TAG, "getLastLoginUserInfo() called" + JsonConvertor.toJson(user));

            }

            Log.d(TAG, "-----------------user--end-------------------------------------- ");
        }
        UserInfo userInfo = db.selector(UserInfo.class).where("isLogin", "=", 1).findFirst();

        Log.d(TAG, "getLastLoginUserInfo() called userInfo:" + JsonConvertor.toJson(userInfo));

        return userInfo;
    }

    /**
     * 根据手机号查询本地用户
     *
     * @param mobile
     * @return
     * @throws Exception
     */
    public UserInfo getUserInfoByMobile(String mobile) throws Exception {
        UserInfo userInfo = db.selector(UserInfo.class).where("mobile", "=", mobile).findFirst();
        return userInfo;
    }

    /**
     * 注销
     *
     * @throws Exception
     */
    public void loginOut() throws Exception {
        WhereBuilder whereBuilder = WhereBuilder.b();
        db.update(UserInfo.class, whereBuilder, new KeyValue("isLogin", 0));//把所有用户登录状态改为未登录
    }


    @Override
    public int saveOrUpdate(UserInfo userInfo) throws Exception {
        Log.d(TAG, "-----------------------保存用户信息：saveOrUpdate() called with: userInfo = [" + JsonConvertor.toJson(userInfo) + "]");
        db.saveOrUpdate(userInfo);
        return 0;
    }

    @Override
    public int insert(UserInfo userInfo) throws Exception {
        Log.d(TAG, "insert() called with: userInfo = [" + JsonConvertor.toJson(userInfo) + "]");
        db.save(userInfo);
        return 1;
    }

    @Override
    public int update(UserInfo userInfo) throws Exception {
        Log.d(TAG, "update() called with: userInfo = [" + JsonConvertor.toJson(userInfo) + "]");
        db.saveOrUpdate(userInfo);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        db.delete(UserInfo.class);
        return 1;
    }

    @Override
    public int delete(int id) throws Exception {
        db.deleteById(UserInfo.class, id);
        return 1;
    }

    @Override
    public int delete(Collection<UserInfo> list) throws Exception {
        return 0;
    }

    @Override
    public UserInfo getEntity(int id) throws Exception {
        return db.findById(UserInfo.class, id);
    }

    @Override
    public List<UserInfo> getList() throws Exception {
        return db.findAll(UserInfo.class);
    }
}
