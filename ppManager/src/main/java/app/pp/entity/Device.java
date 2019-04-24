package app.pp.entity;

import java.util.Date;

public class Device {
    private Integer id;

    private String devicenum;

    private String carnum;

    private Integer state;

    private Date createdtime;

    private Integer synstate;

    private String salenum;

    private String name;

    public String getSalenum() {
        return salenum;
    }

    public void setSalenum(String salenum) {
        this.salenum = salenum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum == null ? null : devicenum.trim();
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Integer getSynstate() {
        return synstate;
    }

    public void setSynstate(Integer synstate) {
        this.synstate = synstate;
    }

}