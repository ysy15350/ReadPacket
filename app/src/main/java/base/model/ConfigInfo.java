package base.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/9/13.
 */

@Table(name = "configInfo")
public class ConfigInfo {

    /**
     * 主键
     */
    @Column(name = "id", isId = true)
    private int id;

    /**
     * 是否是调试模式；1：调试环境；0：正式环境
     */
    @Column(name = "isDebug")
    private int isDebug;


    /**
     * 服务器地址
     */
    @Column(name = "serverUrl")
    private String serverUrl;


    /**
     * 服务器端口号
     */
    @Column(name = "serverPort")
    private int serverPort;

    /**
     * 服务器项目名称
     */
    @Column(name = "projectName")
    private String projectName;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDebug() {
        return isDebug;
    }

    public void setIsDebug(int isDebug) {
        this.isDebug = isDebug;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
