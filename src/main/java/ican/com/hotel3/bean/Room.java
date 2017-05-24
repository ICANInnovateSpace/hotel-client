package ican.com.hotel3.bean;

import java.io.Serializable;


/**
 * Created by mrzhou on 17-5-7.
 *
 */

public class Room implements Serializable{
    private String rid;
    private String rfloor;
    private String rtype;
    private String rprice;
    private String rnum;
    private String rphoto;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRfloor() {
        return rfloor;
    }

    public void setRfloor(String rfloor) {
        this.rfloor = rfloor;
    }

    public String getRprice() {
        return rprice;
    }

    public void setRprice(String rprice) {
        this.rprice = rprice;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getRphoto() {
        return rphoto;
    }

    public void setRphoto(String rphoto) {
        this.rphoto = rphoto;
    }

    @Override
    public String toString() {
        return "Room{" +
                "rid='" + rid + '\'' +
                ", rfloor='" + rfloor + '\'' +
                ", rtype='" + rtype + '\'' +
                ", rprice='" + rprice + '\'' +
                ", rnum='" + rnum + '\'' +
                ", rphoto='" + rphoto + '\'' +
                '}';
    }
}
