package ican.com.hotel3.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by mrzhou on 17-5-7.
 */

public class Record implements Serializable{
    private String rid;
    private String ruid;
    private String rrid;
    private Date rdate;
    private String rdays;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public String getRdays() {
        return rdays;
    }

    public void setRdays(String rdays) {
        this.rdays = rdays;
    }

    @Override
    public String toString() {
        return "Record{" +
                "rid='" + rid + '\'' +
                ", ruid='" + ruid + '\'' +
                ", rrid='" + rrid + '\'' +
                ", rdate=" + rdate +
                ", rdays='" + rdays + '\'' +
                '}';
    }
}
