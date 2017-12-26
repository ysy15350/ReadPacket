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
    private int id;


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

    @Column(name = "uuid")
    private String uuid;//用户唯一标识

    @Column(name = "useridalipay")
    private String useridalipay;//支付宝用户id

    @Column(name = "realname")
    private String realname;//真实姓名

    @Column(name = "recommendationcode")
    private String recommendationcode;//推荐码

    @Column(name = "refereecount")
    private int refereecount;//邀请人数


    @Column(name = "cards")
    private String cards;

    @Column(name = "redpacketCount")
    private int redpacketCount;//抢到红包数量

    @Column(name = "account")
    private double account;//账户金额(最终金额)

    @Column(name = "accountTotal")
    private double accountTotal;//账户总额

    @Column(name = "accountRedpacket")
    private double accountRedpacket;

    @Column(name = "accountRecharge")
    private double accountRecharge;

    @Column(name = "accountLuck")
    private double accountLuck;

    @Column(name = "accountShare")
    private double accountShare;

    @Column(name = "accountRefer")
    private double accountRefer;


    @Column(name = "withdraw")
    private double withdraw;//提现金额

    @Column(name = "withdrawrate")
    private double withdrawrate;//提现费率（0.1代表0.1%）


    @Column(name = "grabchancecount")
    private int grabchancecount;//可发红包次数

    @Column(name = "qrcode")
    private int qrcode;

    @Column(name = "qrcodeurl")
    private String qrcodeurl;

    @Column(name = "inviteimg")
    private int inviteimg;//邀请图片id

    @Column(name = "inviteimgurl")
    private String inviteimgurl;//邀请图片url


    @Column(name = "deviceId")
    private String deviceId;//设备唯一ID

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "headimg")
    private int headimg;

    @Column(name = "headimgurl")
    private String headimgurl;//头像

    @Column(name = "token")
    private String token;//

    @Column(name = "loginTimeStr")
    private String loginTimeStr;

    @Column(name = "createtime")//注册时间
    private long createtime;

    @Column(name = "isLogin")
    private int isLogin;//1:商家；2：经销商


    //-----------------支付宝相关---------------------

    @Column(name = "userid")
    private String userid;//支付宝用户id

    @Column(name = "avatar")
    private String avatar;//支付宝头像

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "userType")
    private String userType;

    @Column(name = "userstatus")
    private String userstatus;


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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUseridalipay() {
        return useridalipay;
    }

    public void setUseridalipay(String useridalipay) {
        this.useridalipay = useridalipay;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public int getRedpacketCount() {
        return redpacketCount;
    }

    public void setRedpacketCount(int redpacketCount) {
        this.redpacketCount = redpacketCount;
    }

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }

    public double getAccountTotal() {
        return accountTotal;
    }

    public void setAccountTotal(double accountTotal) {
        this.accountTotal = accountTotal;
    }

    public double getAccountRedpacket() {
        return accountRedpacket;
    }

    public void setAccountRedpacket(double accountRedpacket) {
        this.accountRedpacket = accountRedpacket;
    }

    public double getAccountRecharge() {
        return accountRecharge;
    }

    public void setAccountRecharge(double accountRecharge) {
        this.accountRecharge = accountRecharge;
    }

    public double getAccountLuck() {
        return accountLuck;
    }

    public void setAccountLuck(double accountLuck) {
        this.accountLuck = accountLuck;
    }

    public double getAccountShare() {
        return accountShare;
    }

    public void setAccountShare(double accountShare) {
        this.accountShare = accountShare;
    }

    public double getAccountRefer() {
        return accountRefer;
    }

    public void setAccountRefer(double accountRefer) {
        this.accountRefer = accountRefer;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus;
    }
}
