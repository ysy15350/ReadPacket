package api.model;

/**
 * Created by yangshiyou on 2017/9/19.
 */

public class OrderInfo {


    /**
     * id : 79
     * ordernum : HB17112721560002
     * uid : 1
     * redPacketId : null
     * type : 3
     * price : 100
     * creattime : 1511790986000
     * status : 0
     */

    private int id;
    private String ordernum;
    private int uid;
    private Object redPacketId;
    private int type;
    private String lucknum;
    private String title;
    private String content;
    private double price;
    private long creattime;
    private String creattimeStr;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Object getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(Object redPacketId) {
        this.redPacketId = redPacketId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLucknum() {
        return lucknum;
    }

    public void setLucknum(String lucknum) {
        this.lucknum = lucknum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCreattime() {
        return creattime;
    }

    public void setCreattime(long creattime) {
        this.creattime = creattime;
    }

    public String getCreattimeStr() {
        return creattimeStr;
    }

    public void setCreattimeStr(String creattimeStr) {
        this.creattimeStr = creattimeStr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
