package ican.com.hotel3.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by mrzhou on 17-5-7.
 *
 */

public class Order implements Serializable{
    private String oid;
    private String ouid;
    private Date odate;
    private String odays;
    private Date oquit;
    private String ototal;
    private String wxNO;
    private String wxPayUrl;
    private Room room;


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getOuid() {
        return ouid;
    }

    public void setOuid(String ouid) {
        this.ouid = ouid;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public String getOdays() {
        return odays;
    }

    public void setOdays(String odays) {
        this.odays = odays;
    }

    public String getOtotal() {
        return ototal;
    }

    public void setOtotal(String ototal) {
        this.ototal = ototal;
    }

    public String getWxNO() {
        return wxNO;
    }

    public void setWxNO(String wxNO) {
        this.wxNO = wxNO;
    }

    public String getWxPayUrl() {
        return wxPayUrl;
    }

    public void setWxPayUrl(String wxPayUrl) {
        this.wxPayUrl = wxPayUrl;
    }

    public Date getOquit() {
        return oquit;
    }

    public void setOquit(Date oquit) {
        this.oquit = oquit;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", ouid='" + ouid + '\'' +
                ", odate=" + odate +
                ", odays='" + odays + '\'' +
                ", oquit=" + oquit +
                ", ototal='" + ototal + '\'' +
                ", wxNO='" + wxNO + '\'' +
                ", wxPayUrl='" + wxPayUrl + '\'' +
                ", room=" + room +
                '}';
    }
}
