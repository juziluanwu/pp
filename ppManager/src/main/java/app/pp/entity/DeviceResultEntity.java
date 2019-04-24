package app.pp.entity;

import java.util.List;

public class DeviceResultEntity {
    private String flag;

    private String msg;

    private List<DeviceResult> obj;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DeviceResult> getObj() {
        return obj;
    }

    public void setObj(List<DeviceResult> obj) {
        this.obj = obj;
    }
}
