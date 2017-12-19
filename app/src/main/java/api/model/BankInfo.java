package api.model;

/**
 * Created by yangshiyou on 2017/9/27.
 */

/**
 * 银行信息列表
 */
public class BankInfo {

    private int id;

    private String bankname;

    private int bankiconid;

    private String bankicon;

    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getBankiconid() {
        return bankiconid;
    }

    public void setBankiconid(int bankiconid) {
        this.bankiconid = bankiconid;
    }

    public String getBankicon() {
        return bankicon;
    }

    public void setBankicon(String bankicon) {
        this.bankicon = bankicon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
