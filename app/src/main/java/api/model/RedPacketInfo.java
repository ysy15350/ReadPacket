package api.model;

import java.io.Serializable;

/**
 * Created by yangshiyou on 2017/11/22.
 */

public class RedPacketInfo implements Serializable {

    private int id;

    private String ordernum;

    private int uid;

    private int price;

    private int num;

    private int packetcount;

    private int luck;

    private int luckcount;

    private int reward;

    private int status;

    private long createtime;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPacketcount() {
        return packetcount;
    }

    public void setPacketcount(int packetcount) {
        this.packetcount = packetcount;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getLuckcount() {
        return luckcount;
    }

    public void setLuckcount(int luckcount) {
        this.luckcount = luckcount;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
