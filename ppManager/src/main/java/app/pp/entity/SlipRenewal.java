package app.pp.entity;

import java.util.Date;

public class SlipRenewal {

    private Integer id;

    private Integer saleslipid;

    private Date date;

    private String opinion;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}