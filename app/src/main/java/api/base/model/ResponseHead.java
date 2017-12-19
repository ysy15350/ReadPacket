package api.base.model;

/**
 * Created by yangshiyou on 2017/11/20.
 */

public class ResponseHead {

    //{"response_status":100,"response_code":"SUCCESS","response_msg":"登录成功","partner_id":null,"service":null,"response_time":"1511186515758","response_time_str":"2017-11-20 22:01:55 758","input_charset":null,"sign_type":null,"sign":null,"login_uid":null,"sessionid":null}

    private int response_status;

    private String response_code;

    private String response_msg;

    private String response_time_str;

    private String login_uid;

    public int getResponse_status() {
        return response_status;
    }

    public void setResponse_status(int response_status) {
        this.response_status = response_status;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getResponse_msg() {
        return response_msg;
    }

    public void setResponse_msg(String response_msg) {
        this.response_msg = response_msg;
    }

    public String getResponse_time_str() {
        return response_time_str;
    }

    public void setResponse_time_str(String response_time_str) {
        this.response_time_str = response_time_str;
    }

    public String getLogin_uid() {
        return login_uid;
    }

    public void setLogin_uid(String login_uid) {
        this.login_uid = login_uid;
    }
}
