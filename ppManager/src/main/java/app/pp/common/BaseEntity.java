package app.pp.common;

import app.pp.utils.UUid;

import java.util.Date;

/**
 * Created by lowdad on 18-1-23.
 */
public class BaseEntity<T> {
    /**
     * 统一生成32位uid
     */
    private String id;
    /**
     * 删除标志（0,未删除 1,已删除）
     */
    private String del_flag;
    /**
     * 创建时间
     */
    private Date createdate;
    /**
     * 更新时间
     */
    private Date updatedate;
    /**
     * 备注
     */
    private String remarks="";

    public BaseEntity() {
        this.id = UUid.uuid();
        this.del_flag = DEL_FLAG_NORMAL;
        this.updatedate=new Date();
    }
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String COMMENFIRST = "0";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDel_flag() {
        return del_flag;
    }

    public void setDel_flag(String del_flag) {
        this.del_flag = del_flag;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
