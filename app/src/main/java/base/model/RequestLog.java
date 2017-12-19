package base.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by yangshiyou on 2017/8/31.
 */

@Table(name = "requestlog")
public class RequestLog implements Serializable {

    @Column(name = "id", isId = true)
    private int id;


    @Column(name = "url")
    private String url;

    @Column(name = "paramStr")
    private String paramStr;

    @Column(name = "authorization")
    private String authorization;

    @Column(name = "token")
    private String token;

    @Column(name = "code")
    private String code;

    @Column(name = "message")
    private String message;

    @Column(name = "responseStr")
    private String responseStr;

    @Column(name = "isCache")
    private Boolean isCache;

    @Column(name = "cacheTime")
    private Integer cacheTime;

    @Column(name = "requestTime")
    private String requestTime;

    @Column(name = "responseTime")
    private String responseTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public void setResponseStr(String responseStr) {
        this.responseStr = responseStr;
    }

    public Boolean getCache() {
        return isCache;
    }

    public void setCache(Boolean cache) {
        isCache = cache;
    }

    public Integer getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Integer cacheTime) {
        this.cacheTime = cacheTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
