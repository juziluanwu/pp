package app.pp.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SaleSlip {

    private Integer id;

    private String salenum;//销售单号

    private Integer deviceid;//设备id

    private String devicenum;//设备号

    private String devicenum2;//设备号

    private Integer policyid;//保单id

    private String pnum;//保单号

    private Integer printstate;//打印状态 1未打印 2已打印

    private Integer policydate;//保单期限 年

    private Date pstarttime;//保险开始日期

    private Date pendtime;//保险结束日期

    private String phone;//电话

    private String address;//地址

    private Integer customertype;//客户类型  1 个人 2企业

    private String customername;//客户名称

    private Integer certificatetype;//证件类型 1身份证2护照3其他

    private String certificatenum;//证件号码

    private String shop4s;//4s店

    private String carnum;//车牌

    private String enginenum;//发动机号码

    private String frame;//车架号码

    private Date buytime;//购车时间

    private Integer salemanid;//销售id

    private Integer carbrandid;//车辆品牌id

    private Integer carsysid;//车系id


    private String carbrand;//车辆品牌

    private String carsys;//车系

    private Integer buycartype;// 1贷款车 2全款车

    private Integer firstbeneficiary;//第一受益人分组id  分组中的金融公司

    private BigDecimal carprice;//新车购入价格

    private BigDecimal compensation;//赔付价格

    private Date installtime;//安装时间

    private String installman;//安装人

    private BigDecimal policyamount;// 保单金额

    private Integer groupid;//分组id

    private Date createdtime;

    private Long creator;

    private Date updatedtime;

    private Long updator;

    private Integer policystate;//保单状态

    private String  creatorname;//录入人

    private String  firstbeneficiaryname;//第一受益人

    private Integer delstate;//废弃状态

    //private Integer printlimit;//打印期限

    private String installaddress;

    private TyreSsinfo tyreSsinfo;


    private  String jxsname;

    private String jxsphone;

    private String jxsaddr;

    private String servicenum;

    private BigDecimal servicetotalamount;

    private List<SaleslipModel> saleslipModels;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalenum() {
        return salenum;
    }

    public void setSalenum(String salenum) {
        this.salenum = salenum;
    }

    public Integer getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(Integer deviceid) {
        this.deviceid = deviceid;
    }

    public Integer getPolicyid() {
        return policyid;
    }

    public void setPolicyid(Integer policyid) {
        this.policyid = policyid;
    }

    public Integer getPrintstate() {
        return printstate;
    }

    public void setPrintstate(Integer printstate) {
        this.printstate = printstate;
    }

    public Integer getPolicydate() {
        return policydate;
    }

    public void setPolicydate(Integer policydate) {
        this.policydate = policydate;
    }

    public Date getPstarttime() {
        return pstarttime;
    }

    public void setPstarttime(Date pstarttime) {
        this.pstarttime = pstarttime;
    }

    public Date getPendtime() {
        return pendtime;
    }

    public void setPendtime(Date pendtime) {
        this.pendtime = pendtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCustomertype() {
        return customertype;
    }

    public void setCustomertype(Integer customertype) {
        this.customertype = customertype;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public Integer getCertificatetype() {
        return certificatetype;
    }

    public void setCertificatetype(Integer certificatetype) {
        this.certificatetype = certificatetype;
    }

    public String getCertificatenum() {
        return certificatenum;
    }

    public void setCertificatenum(String certificatenum) {
        this.certificatenum = certificatenum;
    }

    public String getShop4s() {
        return shop4s;
    }

    public void setShop4s(String shop4s) {
        this.shop4s = shop4s;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getEnginenum() {
        return enginenum;
    }

    public void setEnginenum(String enginenum) {
        this.enginenum = enginenum;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public Date getBuytime() {
        return buytime;
    }

    public void setBuytime(Date buytime) {
        this.buytime = buytime;
    }

    public Integer getSalemanid() {
        return salemanid;
    }

    public void setSalemanid(Integer salemanid) {
        this.salemanid = salemanid;
    }

    public Integer getCarbrandid() {
        return carbrandid;
    }

    public void setCarbrandid(Integer carbrandid) {
        this.carbrandid = carbrandid;
    }

    public Integer getCarsysid() {
        return carsysid;
    }

    public void setCarsysid(Integer carsysid) {
        this.carsysid = carsysid;
    }

    public Integer getBuycartype() {
        return buycartype;
    }

    public void setBuycartype(Integer buycartype) {
        this.buycartype = buycartype;
    }

    public Integer getFirstbeneficiary() {
        return firstbeneficiary;
    }

    public void setFirstbeneficiary(Integer firstbeneficiary) {
        this.firstbeneficiary = firstbeneficiary;
    }

    public BigDecimal getCarprice() {
        return carprice;
    }

    public void setCarprice(BigDecimal carprice) {
        this.carprice = carprice;
    }

    public BigDecimal getCompensation() {
        return compensation;
    }

    public void setCompensation(BigDecimal compensation) {
        this.compensation = compensation;
    }

    public Date getInstalltime() {
        return installtime;
    }

    public void setInstalltime(Date installtime) {
        this.installtime = installtime;
    }

    public String getInstallman() {
        return installman;
    }

    public void setInstallman(String installman) {
        this.installman = installman;
    }

    public BigDecimal getPolicyamount() {
        return policyamount;
    }

    public void setPolicyamount(BigDecimal policyamount) {
        this.policyamount = policyamount;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
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

    public Date getUpdatedtime() {
        return updatedtime;
    }

    public void setUpdatedtime(Date updatedtime) {
        this.updatedtime = updatedtime;
    }

    public Long getUpdator() {
        return updator;
    }

    public void setUpdator(Long updator) {
        this.updator = updator;
    }

    public Integer getPolicystate() {
        return policystate;
    }

    public void setPolicystate(Integer policystate) {
        this.policystate = policystate;
    }

    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum;
    }

    public String getPnum() {
        return pnum;
    }

    public void setPnum(String pnum) {
        this.pnum = pnum;
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public String getFirstbeneficiaryname() {
        return firstbeneficiaryname;
    }

    public void setFirstbeneficiaryname(String firstbeneficiaryname) {
        this.firstbeneficiaryname = firstbeneficiaryname;
    }

    public Integer getDelstate() {
        return delstate;
    }

    public void setDelstate(Integer delstate) {
        this.delstate = delstate;
    }

    public String getDevicenum2() {
        return devicenum2;
    }

    public void setDevicenum2(String devicenum2) {
        this.devicenum2 = devicenum2;
    }

    public String getInstalladdress() {
        return installaddress;
    }

    public void setInstalladdress(String installaddress) {
        this.installaddress = installaddress;
    }

    public TyreSsinfo getTyreSsinfo() {
        return tyreSsinfo;
    }

    public void setTyreSsinfo(TyreSsinfo tyreSsinfo) {
        this.tyreSsinfo = tyreSsinfo;
    }

    public String getJxsname() {
        return jxsname;
    }

    public void setJxsname(String jxsname) {
        this.jxsname = jxsname;
    }

    public String getJxsphone() {
        return jxsphone;
    }

    public void setJxsphone(String jxsphone) {
        this.jxsphone = jxsphone;
    }

    public String getJxsaddr() {
        return jxsaddr;
    }

    public void setJxsaddr(String jxsaddr) {
        this.jxsaddr = jxsaddr;
    }

    public String getServicenum() {
        return servicenum;
    }

    public void setServicenum(String servicenum) {
        this.servicenum = servicenum;
    }

    public BigDecimal getServicetotalamount() {
        return servicetotalamount;
    }

    public void setServicetotalamount(BigDecimal servicetotalamount) {
        this.servicetotalamount = servicetotalamount;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public String getCarsys() {
        return carsys;
    }

    public void setCarsys(String carsys) {
        this.carsys = carsys;
    }

    public List<SaleslipModel> getSaleslipModels() {
        return saleslipModels;
    }

    public void setSaleslipModels(List<SaleslipModel> saleslipModels) {
        this.saleslipModels = saleslipModels;
    }
}