package api.model;

import java.io.Serializable;

/**
 * Created by yangshiyou on 2017/11/26.
 */

public class RedPacketDetail implements Serializable {


    /**
     * id : 490
     * seq : 5
     * ordernum : HB17112523110001
     * redPacketId : 89
     * uid : 1
     * luckNum : null
     * redPacketPrice : 200
     * price : 23.68
     * priceActual : null
     * type : 2
     * status : 1
     * createtime : 1511622689000
     * grabtime : 1511674736000
     */

    private int id;
    private int seq;
    private String ordernum;
    private int redPacketId;
    private int uid;
    private String luckNum;
    private int redPacketPrice;
    private double price;
    private double priceActual;
    private int type;
    private int status;
    private long createtime;
    private long grabtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public int getRedPacketId() {
        return redPacketId;
    }

    public void setRedPacketId(int redPacketId) {
        this.redPacketId = redPacketId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLuckNum() {
        return luckNum;
    }

    public void setLuckNum(String luckNum) {
        this.luckNum = luckNum;
    }

    public int getRedPacketPrice() {
        return redPacketPrice;
    }

    public void setRedPacketPrice(int redPacketPrice) {
        this.redPacketPrice = redPacketPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceActual() {
        return priceActual;
    }

    public void setPriceActual(double priceActual) {
        this.priceActual = priceActual;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public long getGrabtime() {
        return grabtime;
    }

    public void setGrabtime(long grabtime) {
        this.grabtime = grabtime;
    }
}
