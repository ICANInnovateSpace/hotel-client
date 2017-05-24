package ican.com.hotel3.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2017/4/18.
 */

public class User implements Serializable{
    private String uid;
    private String uname;
    private String ureal;
    private String usex;
    private Date uborn;
    private String uphone;
    private String uphoto;
    private String ucard;
    private String ubank;
    private String upsw;
    private String ustate;
    private List<Order> orders;
    private List<Record> records;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUreal() {
        return ureal;
    }

    public void setUreal(String ureal) {
        this.ureal = ureal;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUcard() {
        return ucard;
    }

    public String getUbank() {
        return ubank;
    }

    public void setUbank(String ubank) {
        this.ubank = ubank;
    }

    public void setUcard(String ucard) {
        this.ucard = ucard;
    }

    public String getUphoto() {
        return uphoto;
    }

    public void setUphoto(String uphoto) {
        this.uphoto = uphoto;
    }

    public Date getUborn() {
        return uborn;
    }

    public void setUborn(Date uborn) {
        this.uborn = uborn;
    }

    public String getUstate() {
        return ustate;
    }

    public void setUstate(String ustate) {
        this.ustate = ustate;
    }

    public String getUpsw() {
        return upsw;
    }

    public void setUpsw(String upsw) {
        this.upsw = upsw;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", ureal='" + ureal + '\'' +
                ", usex='" + usex + '\'' +
                ", uborn=" + uborn +
                ", uphone='" + uphone + '\'' +
                ", uphoto='" + uphoto + '\'' +
                ", ucard='" + ucard + '\'' +
                ", ubank='" + ubank + '\'' +
                ", upsw='" + upsw + '\'' +
                ", ustate='" + ustate + '\'' +
                ", records=" + records +
                '}';
    }
}
