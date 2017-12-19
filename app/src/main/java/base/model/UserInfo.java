package base.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by yangshiyou on 2017/9/13.
 */

@Table(name = "userInfo")
public class UserInfo {

//    {"code":200,"result":
// {"headimgurl":"\/Uploads\/Picture\/2017-09-26\/59c9d06ed6614.jpeg",
// "nickname":"aa","mobile":"15212341234",
// "cards":"65565656","address":"aaaafffdd",
// "balance":"0.00",
// "total_price":0,
// "day_total_price":0,
// "rate":"9.9"}}


    /**
     * 主键
     */
    @Column(name = "uid", isId = true, autoGen = true)
    private int uid;

    @Column(name = "id")
    private int id;//1:商家；2：经销商


    @Column(name = "mobile")
    private String mobile;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @Column(name = "pay_password")
    private String pay_password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "recommendationcode")
    private String recommendationcode;

    @Column(name = "refereecount")
    private int refereecount;


    @Column(name = "cards")
    private String cards;

    @Column(name = "account")
    private double account;

    @Column(name = "withdraw")
    private double withdraw;

    @Column(name = "withdrawrate")
    private double withdrawrate;


    @Column(name = "grabchancecount")
    private int grabchancecount;

    @Column(name = "qrcode")
    private int qrcode;

    @Column(name = "qrcodeurl")
    private String qrcodeurl;

    @Column(name = "inviteimg")
    private int inviteimg;

    @Column(name = "inviteimgurl")
    private String inviteimgurl;


    @Column(name = "deviceId")
    private String deviceId;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "headimg")
    private int headimg;

    @Column(name = "headimgurl")
    private String headimgurl;//图片id

    @Column(name = "token")
    private String token;//图片id

    @Column(name = "loginTimeStr")
    private String loginTimeStr;

    @Column(name = "createtime")//注册时间
    private long createtime;

    @Column(name = "isLogin")
    private int isLogin;//1:商家；2：经销商


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRecommendationcode() {
        return recommendationcode;
    }

    public void setRecommendationcode(String recommendationcode) {
        this.recommendationcode = recommendationcode;
    }

    public int getRefereecount() {
        return refereecount;
    }

    public void setRefereecount(int refereecount) {
        this.refereecount = refereecount;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(double withdraw) {
        this.withdraw = withdraw;
    }

    public double getWithdrawrate() {
        return withdrawrate;
    }

    public void setWithdrawrate(double withdrawrate) {
        this.withdrawrate = withdrawrate;
    }

    public int getGrabchancecount() {
        return grabchancecount;
    }

    public void setGrabchancecount(int grabchancecount) {
        this.grabchancecount = grabchancecount;
    }

    public int getQrcode() {
        return qrcode;
    }

    public void setQrcode(int qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcodeurl() {
        return qrcodeurl;
    }

    public void setQrcodeurl(String qrcodeurl) {
        this.qrcodeurl = qrcodeurl;
    }

    public int getInviteimg() {
        return inviteimg;
    }

    public void setInviteimg(int inviteimg) {
        this.inviteimg = inviteimg;
    }

    public String getInviteimgurl() {
        return inviteimgurl;
    }

    public void setInviteimgurl(String inviteimgurl) {
        this.inviteimgurl = inviteimgurl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getHeadimg() {
        return headimg;
    }

    public void setHeadimg(int headimg) {
        this.headimg = headimg;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginTimeStr() {
        return loginTimeStr;
    }

    public void setLoginTimeStr(String loginTimeStr) {
        this.loginTimeStr = loginTimeStr;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
}
