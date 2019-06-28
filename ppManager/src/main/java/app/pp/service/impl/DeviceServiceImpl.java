package app.pp.service.impl;


import app.pp.common.Result;
import app.pp.entity.Device;
import app.pp.entity.DeviceResult;
import app.pp.entity.DeviceResultEntity;
import app.pp.entity.PolicyEntity;
import app.pp.enums.ErrorEnum;
import app.pp.exceptions.GlobleException;
import app.pp.mapper.DeviceMapper;
import app.pp.service.DeviceService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    DeviceMapper deviceMapper;

    private static Logger logger = Logger.getLogger(DeviceServiceImpl.class);

    public static String getUrlByMap(String url, Map<String,Object> map){
        if(StringUtils.isEmpty(url) || map == null){
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        if(!map.isEmpty()){
            stringBuilder.append("?");
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    //获取设备号
    @Override
    public  void getDevice()throws Exception {
        //调用的地址
        String ip = "http://120.76.69.92:7515";
        //接口名称
        String urlName = "/VehicleData/GetVehicleBaseData.json";
        //调用地址
        String apiAddress = ip + urlName;
        //参数
        Map<String, Object> map = new HashMap<>();
        //调用申请api密匙接口
        DeviceResultEntity deviceResultEntity = null;
        map.put("type", 1);
        map.put("key", "6e1a70bd-06ef-45e0-9d8a-8330472f68e3");

        String url = getUrlByMap(apiAddress, map);
        try {
            url = url.replaceAll(" ", "%20");
            String result = httpGet(url);
            //json转对象
            deviceResultEntity = JSON.parseObject(result, DeviceResultEntity.class);
            //得到结果循环在库里查询
            //如果为1证明请求接口没问题
            if(deviceResultEntity.getFlag().equals("1")){
                //从库里查询所有的设备号
            List<Device> devices = deviceMapper.selectAll();
                ArrayList<Device> data = new ArrayList<>();
                for(int i = 0;i<deviceResultEntity.getObj().size();i++){
                int no = 0;
                for(Device device : devices){
                    if(deviceResultEntity.getObj().get(i).getTerminalNo().equals(device.getDevicenum())){
                        no = 1;
                        //如果相同就删除当前list中的元素
                        if(device.getSynstate()==2){
                            deviceMapper.updateSynState(device.getId(),1);
                        }
                        if(!device.getCarnum().equals(deviceResultEntity.getObj().get(i).getId())){
                            device.setCarnum(deviceResultEntity.getObj().get(i).getId());
                            deviceMapper.updateByPrimaryKeySelective(device);
                        }
                       //deviceResultEntity.getObj().remove(i);
                    }
                }
                if(no==0){
                    Device device = new Device();
                    device.setDevicenum(deviceResultEntity.getObj().get(i).getTerminalNo());
                    device.setCarnum(deviceResultEntity.getObj().get(i).getId());
                    device.setCreatedtime(new Date());
                    device.setState(1);
                    device.setSynstate(2);
                    data.add(device);
                }
            }
                if(data.size()>0){
                    deviceMapper.insert(data);
                }



            //保存完新的设备号后在做是否修改设备号状态操作
                List<Device> devices2 = deviceMapper.selectAll();
                DeviceResultEntity  deviceResultEntity2 = JSON.parseObject(result, DeviceResultEntity.class);
                for(int i = 0;i<devices2.size();i++){
                    for(DeviceResult deviceResult : deviceResultEntity2.getObj()){
                        if(devices2.get(i).getDevicenum().equals(deviceResult.getTerminalNo())){
                            devices2.remove(i);
                        }
                    }
                }
                //如果操作完成后devices2中的长度大于0的话就证明有报废单的设备号
                if(devices2.size()>0){
                    for(Device device : devices2){
                        deviceMapper.updateSynState(device.getId(),2);
                    }
                }

            //获得结果
            System.err.println(result);
            }else{
                logger.info("请求接口失败");
            }
        } catch (Exception e) {
            System.err.println("报错" + e.getLocalizedMessage());
        }

    }

    @Override
    //获取设备号列表
    public Result list(Integer page) {

        PageHelper.startPage(null == page ? 1 : page, GlobleUtils.DEFAULT_PAGE_SIZE);
        PageInfo<Device> pageInfo = new PageInfo<Device>(deviceMapper.list());

        return ResultUtils.result(ErrorEnum.SUCCESS,pageInfo);
    }

    public  static String httpGet(String url) throws Exception {
        // 调用接口，打开链接
        String inputLine = "";
        URL fixposition = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) fixposition.openConnection();// 打开连接
        //必须设置时间了，不然卡死
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(1000);
        connection.connect();// 连接会话
        BufferedReader in = new BufferedReader(new InputStreamReader(fixposition.openStream(), "UTF-8"));
        try {
            String readLine;
            while ((readLine = in.readLine()) != null) {
                inputLine = inputLine + readLine;
            }
        } finally {
            in.close();
        }
        return inputLine;

    }

    public Integer testDevice(String devicenum){
        Device d = deviceMapper.selectDeviceExsit(devicenum);
        if(d== null){
            throw new GlobleException("设备号不存在");
        }else{
            if(2 == d.getState()){
                throw new GlobleException("设备号已被别的销售单绑定");
            }else if(3 == d.getState()){
                throw new GlobleException("设备号已被作废");
            }
        }
        return d.getId();
    }
}
