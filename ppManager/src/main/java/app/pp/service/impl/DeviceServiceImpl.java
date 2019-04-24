package app.pp.service.impl;


import app.pp.common.Result;
import app.pp.entity.Device;
import app.pp.entity.DeviceResult;
import app.pp.entity.DeviceResultEntity;
import app.pp.entity.PolicyEntity;
import app.pp.enums.ErrorEnum;
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
    public  void getDevice() {
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
        map.put("key", "1f87dd41-87c9-46a0-b74a-24e47a587a4e");

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
            for(int i = 0;i<deviceResultEntity.getObj().size();i++){
                for(Device device : devices){
                    if(deviceResultEntity.getObj().get(i).getTerminalNo().equals(device.getDevicenum())){
                        //如果相同就删除当前list中的元素
                        if(device.getSynstate()==2){
                            deviceMapper.updateSynState(device.getId(),1);
                        }
                       deviceResultEntity.getObj().remove(i);
                    }
                }
            }
            //循环完成之后如果查询出的list的长度大于0就做插入操作
            List<Device> list = new ArrayList<>();
            if(deviceResultEntity.getObj().size()>0){
                for(DeviceResult deviceResult : deviceResultEntity.getObj()){
                    Device device = new Device();
                    device.setDevicenum(deviceResult.getTerminalNo());
                    device.setCarnum(deviceResult.getPlate());
                    device.setCreatedtime(new Date());
                    device.setState(1);
                    device.setSynstate(2);
                    list.add(device);
                }
                deviceMapper.insert(list);
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


}
