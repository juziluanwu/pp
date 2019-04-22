package app.pp.entity;

import java.util.List;

public class TransferCarEntity {

    private Integer  groupid;

    private List<Integer> policys;

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public List<Integer> getPolicys() {
        return policys;
    }

    public void setPolicys(List<Integer> policys) {
        this.policys = policys;
    }
}
