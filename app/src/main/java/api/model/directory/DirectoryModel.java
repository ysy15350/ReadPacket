package api.model.directory;

import java.util.List;

/**
 * Created by yangshiyou on 2017/11/9.
 */

public class DirectoryModel {


    /**
     * status : 1
     * msg :
     * aid : 1691
     * articlename : 盗贼求生计
     * authorid : 17013
     * author : 小道
     * lastupdate : 20170323210638
     * data : [{"chapterid":"72142","chaptertype":"1","chaptername":"第一卷","isvip":"0","saleprice":"0","postdate":"20170106165625","lastupdate":"20170106165625","words":0},{"chapterid":"19264","chaptertype":"0","chaptername":"楔子","isvip":"0","saleprice":"0","postdate":"20160310231026","lastupdate":"20160504220618","words":374},{"chapterid":"19265","chaptertype":"0","chaptername":"第一章   薇儿","isvip":"0","saleprice":"0","postdate":"20160310231314","lastupdate":"20160819121748","words":3214},{"chapterid":"19594","chaptertype":"0","chaptername":"第二章  索索&#12539;谭尔特","isvip":"0","saleprice":"0","postdate":"20160312003946","lastupdate":"20160312003946","words":2942},{"chapterid":"20059","chaptertype":"0","chaptername":"第三章 红发安妮","isvip":"0","saleprice":"0","postdate":"20160313152519","lastupdate":"20160313152519","words":1965},{"chapterid":"20141","chaptertype":"0","chaptername":"第四章 得手","isvip":"0","saleprice":"0","postdate":"20160313205030","lastupdate":"20160313205030","words":2021},{"chapterid":"20143","chaptertype":"0","chaptername":"第五章 雇主","isvip":"0","saleprice":"0","postdate":"20160313205335","lastupdate":"20160313205335","words":1427},{"chapterid":"26718","chaptertype":"0","chaptername":"第六章  再次交易","isvip":"0","saleprice":"6","postdate":"20160410110932","lastupdate":"20160505013837","words":2031},{"chapterid":"72143","chaptertype":"1","chaptername":"第二卷","isvip":"0","saleprice":"0","postdate":"20170106165740","lastupdate":"20170106165740","words":0},{"chapterid":"28422","chaptertype":"0","chaptername":"第七章  单.夏","isvip":"0","saleprice":"5","postdate":"20160420084728","lastupdate":"20160819122009","words":1611},{"chapterid":"48157","chaptertype":"0","chaptername":"第八章   上课","isvip":"0","saleprice":"8","postdate":"20160629212430","lastupdate":"20160819115637","words":2671},{"chapterid":"54933","chaptertype":"0","chaptername":"第九章 猫儿","isvip":"0","saleprice":"0","postdate":"20160813210339","lastupdate":"20170226155423","words":2152},{"chapterid":"55976","chaptertype":"0","chaptername":"第十章  遭遇","isvip":"0","saleprice":"6","postdate":"20160820132751","lastupdate":"20160820132751","words":1969},{"chapterid":"57151","chaptertype":"0","chaptername":"第十一章 交流","isvip":"0","saleprice":"0","postdate":"20160827210406","lastupdate":"20170323210638","words":3074}]
     */

    private int status;
    private String msg;
    private String aid;
    private String articlename;
    private String authorid;
    private String author;
    private String lastupdate;
    private List<DirectoryInfo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getArticlename() {
        return articlename;
    }

    public void setArticlename(String articlename) {
        this.articlename = articlename;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public List<DirectoryInfo> getData() {
        return data;
    }

    public void setData(List<DirectoryInfo> data) {
        this.data = data;
    }


}
