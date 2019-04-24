package app.pp.entity;

import java.util.Date;

public class PrintRecord {

    private Integer id;

    private Integer saleslipid;

    private Integer printlimit;

    private Date pstarttime;

    private Date createdtime;

    private Long creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Integer getSaleslipid() {
        return saleslipid;
    }

    public void setSaleslipid(Integer saleslipid) {
        this.saleslipid = saleslipid;
    }

    public Integer getPrintlimit() {
        return printlimit;
    }

    public void setPrintlimit(Integer printlimit) {
        this.printlimit = printlimit;
    }

    public Date getPstarttime() {
        return pstarttime;
    }

    public void setPstarttime(Date pstarttime) {
        this.pstarttime = pstarttime;
    }
}