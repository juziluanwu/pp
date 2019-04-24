package app.pp.service;

import app.pp.common.Result;

public interface DeviceService {

    //获取设备号
    public void getDevice() throws Exception;

    //设备号列表
    public Result list(Integer page);
}
