package api.model;

/**
 * Created by yangshiyou on 2017/12/8.
 */

public class PrizeInfo {


    private double totalPrize;//总收益
    private double yesterdaySharePrize;//昨日分享奖励
    private double yesterdayRedPacketPrize;//昨日红包奖励
    private double totalSharePrize;//分享总奖励
    private double totalRedPacketPrize;//红包总奖励


    public double getTotalPrize() {
        return totalPrize;
    }

    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }

    public double getYesterdaySharePrize() {
        return yesterdaySharePrize;
    }

    public void setYesterdaySharePrize(double yesterdaySharePrize) {
        this.yesterdaySharePrize = yesterdaySharePrize;
    }

    public double getYesterdayRedPacketPrize() {
        return yesterdayRedPacketPrize;
    }

    public void setYesterdayRedPacketPrize(double yesterdayRedPacketPrize) {
        this.yesterdayRedPacketPrize = yesterdayRedPacketPrize;
    }

    public double getTotalSharePrize() {
        return totalSharePrize;
    }

    public void setTotalSharePrize(double totalSharePrize) {
        this.totalSharePrize = totalSharePrize;
    }

    public double getTotalRedPacketPrize() {
        return totalRedPacketPrize;
    }

    public void setTotalRedPacketPrize(double totalRedPacketPrize) {
        this.totalRedPacketPrize = totalRedPacketPrize;
    }
}
