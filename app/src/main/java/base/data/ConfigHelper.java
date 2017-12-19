package base.data;

import java.util.Collection;
import java.util.List;

import api.base.model.Config;
import base.model.ConfigInfo;
import common.CommFun;
import common.database.DbUtilsXutils3;

/**
 * Created by yangshiyou on 2017/9/13.
 */

public class ConfigHelper extends DbUtilsXutils3<ConfigInfo> {

    private static ConfigHelper configHelper;

    private ConfigHelper() {

    }


    private static ConfigHelper getInstance() {
        if (configHelper == null) {
            configHelper = new ConfigHelper();
            init();
        }
        return configHelper;
    }

    /**
     * 初始化配置，启动时调用
     */
    public static void initConfigInfo() {


        //------------------正式------------------------------

        ConfigInfo configInfo = new ConfigInfo();
        configInfo.setIsDebug(0);
        configInfo.setServerUrl("www.360vrdh.com");
        configInfo.setServerPort(8080);
        configInfo.setProjectName("api");

        //------------------测试------------------------------

        ConfigInfo configInfo1 = new ConfigInfo();
        configInfo1.setIsDebug(1);
        configInfo1.setServerUrl("192.168.31.176");//192.168.31.176  192.168.0.144
        configInfo1.setServerPort(8080);
        configInfo1.setProjectName("api");

        try {

            if (configHelper == null)
                configHelper = getInstance();

            configHelper.delete();

            configHelper.saveOrUpdate(configInfo);
            configHelper.saveOrUpdate(configInfo1);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取配置信息
     *
     * @param isDebug 0：正式；1：调试
     * @return
     */
    public static ConfigInfo getConfigInfo(int isDebug) {
        try {


            List<ConfigInfo> list1 = db.selector(ConfigInfo.class).where("isDebug", "=", isDebug).findAll();
            if (list1 != null && !list1.isEmpty()) {

                ConfigInfo configInfo = list1.get(0);

                return configInfo;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取接口访问地址
     *
     * @param withProjectName 是否包含项目名称
     * @return
     */
    public static String getServerUrl(boolean withProjectName) {
        try {
            ConfigInfo configInfo = null;
            if (Config.isDebug) {
                configInfo = ConfigHelper.getConfigInfo(1);//isDebug 0：正式；1：调试
            } else {
                configInfo = ConfigHelper.getConfigInfo(0);//isDebug 0：正式；1：调试
            }

            if (configInfo != null) {
                //int isDebug = configInfo.getIsDebug();
                String server_url = configInfo.getServerUrl();
                int server_port = configInfo.getServerPort();
                String projectName = configInfo.getProjectName();

                String serverUrl = String.format("http://%s:%d/", server_url, server_port);

                if (withProjectName)
                    serverUrl = String.format("http://%s:%d/%s/", server_url, server_port, projectName);

                return serverUrl;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取图片显示路径前部分，最后只需跟fid即可
     *
     * @return
     */
    public static String getServerImageUrl() {
        try {
            String url = getServerUrl(true);
            if (!CommFun.isNullOrEmpty(url)) {
                url += "/file/imgGet?fid=";
                return url;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public int saveOrUpdate(ConfigInfo configInfo) throws Exception {
        db.saveOrUpdate(configInfo);

        return 1;
    }

    @Override
    public int insert(ConfigInfo configInfo) throws Exception {
        db.save(configInfo);
        return 1;
    }

    @Override
    public int update(ConfigInfo configInfo) throws Exception {
        db.saveOrUpdate(configInfo);
        return 1;
    }

    @Override
    public int delete() throws Exception {
        db.delete(ConfigInfo.class);
        return 1;
    }

    @Override
    public int delete(int id) throws Exception {
        return 0;
    }

    @Override
    public int delete(Collection<ConfigInfo> list) throws Exception {
        return 0;
    }

    @Override
    public ConfigInfo getEntity(int id) throws Exception {
        return db.findById(ConfigInfo.class, id);
    }

    @Override
    public List<ConfigInfo> getList() throws Exception {
        return db.findAll(ConfigInfo.class);
    }

    /**
     * 　┏┓　 ┏┓
     * ┏┛┻━━━┛┻┓
     * ┃　　　　　 |
     * ┃　　　━　　┃
     * ┃　┳┛　┗┳　 ┃
     * ┃　　　　　 |
     * ┃　　　┻　　┃
     * ┃　　　　　 |
     * ┗━┓　　　┏━┛
     * 　┃　　　┃神兽保佑
     * 　┃　　　┃代码无BUG！
     * 　┃　　　┗━━━┓
     * 　┃　　　　　　　┣┓
     * 　┃　　　　　　　┏┛
     * 　┗┓┓┏━┳┓┏┛
     * 　┃┫┫　┃┫┫
     * 　┗┻┛　┗┻┛
     */


    /**
     * *
     *----------Dragon be here!----------/
     * 　　　┏┓　　　┏┓
     * 　　┏┛┻━━━┛┻┓
     * 　　┃　　　　　　　┃
     * 　　┃　　　━　　　┃
     * 　　┃　┳┛　┗┳　┃
     * 　　┃　　　　　　　┃
     * 　　┃　　　┻　　　┃
     * 　　┃　　　　　　　┃
     * 　　┗━┓　　　┏━┛
     * 　　　　┃　　　┃神兽保佑
     * 　　　　┃　　　┃代码无BUG！
     * 　　　　┃　　　┗━━━┓
     * 　　　　┃　　　　　　　┣┓
     * 　　　　┃　　　　　　　┏┛
     * 　　　　┗┓┓┏━┳┓┏┛
     * 　　　　　┃┫┫　┃┫┫
     * 　　　　　┗┻┛　┗┻┛
     * ━━━━━━神兽出没━━━━━━
     */

    /**
     *
     * ━━━━━━神兽出没━━━━━━
     * 　　　┏┓　　　┏┓
     * 　　┏┛┻━━━┛┻┓
     * 　　┃　　　　　　　┃
     * 　　┃　　　━　　　┃
     * 　　┃　┳┛　┗┳　┃
     * 　　┃　　　　　　　┃
     * 　　┃　　　┻　　　┃
     * 　　┃　　　　　　　┃
     * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
     * 　　　　┃　　　┃    神兽保佑,代码无bug
     * 　　　　┃　　　┃
     * 　　　　┃　　　┗━━━┓
     * 　　　　┃　　　　　　　┣┓
     * 　　　　┃　　　　　　　┏┛
     * 　　　　┗┓┓┏━┳┓┏┛
     * 　　　　　┃┫┫　┃┫┫
     * 　　　　　┗┻┛　┗┻┛
     *
     * ━━━━━━感觉萌萌哒━━━━━━
     */

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃ 　
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting　　　　　　　　　　
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃　　　　　　　　　　　
 * 　　　　　　　　　┃　　　┃  　　　　　　
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　　　　　　　　　　
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */

/**
 *　　　　　　　　┏┓　　　┏┓+ +
 *　　　　　　　┏┛┻━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　　┃ 　
 *　　　　　　　┃　　　━　　　┃ ++ + + +
 *　　　　　　 ████━████ ┃+
 *　　　　　　　┃　　　　　　　┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　　┃ + +
 *　　　　　　　┗━┓　　　┏━┛
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　
 *　　　　　　　　　┃　　　┃ + + + +
 *　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug　　
 *　　　　　　　　　┃　　　┃
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　
 *　　　　　　　　　┃　 　　┗━━━┓ + +
 *　　　　　　　　　┃ 　　　　　　　┣┓
 *　　　　　　　　　┃ 　　　　　　　┏┛
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　　　　　　　┃┫┫　┃┫┫
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
}
