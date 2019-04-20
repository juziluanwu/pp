package app.pp.enums;

/**
 * Created by wcy on 2018/2/24.
 */
public enum DictEnum {
    /**
     * 征信记录枚举
     */
    NO_OVERDUE("1","无逾期","creditRecord"),
    OD_BETWEEN_ONEANDTHREE("2","逾期1-3次","creditRecord"),
    OD_BETWEEN_THREEANDFIVE("3","逾期3-5次","creditRecord"),
    OD_OVER_FIVE("4","逾期5次以上","creditRecord"),
    /**
     * 职业枚举
     */
    WOKER("1","上班族","job"),
    BOSS("2","法人","job"),
    HOUSEHODE("3","个体","job"),
    JOBLESS("4","无业","job"),
    /**
     * 工作经验枚举
     */
    E_IN_HALF_YEAR("1","半年以内","experience"),
    E_BETWEEN_HALFANDONE_YEAR("2","半年到一年","experience"),
    E_OVER_ONE_YEAR("3","一年以上","experience"),
    /**
     * 贷款期限枚举
     */
    S_IN_ONE_YEAR("1","一年以内","stages"),
    S_BETWEEM_ONEANDTHREE_YEAR("2","1-3年","stages"),
    S_OVER_THREE_YEAR("3","3年以上","stages"),
    /**
     * 户籍枚举
     */
    NATIVE("1","本地人","familyRegister"),
    FOREIGNER("0","外地人","familyRegister"),
    /**
     * 学历
     */
    OVER_COLLEGE("1","本科及其以上","education"),
    JUNIOR("2","大专","education"),
    UNDER_JUNIOR("3","大专以下","education"),
    /**
     * 月收入
     */
    monthlyIncomeL1("1","2000以下","monthlyIncome"),
    monthlyIncomeL2("2","2000-3500","monthlyIncome"),
    monthlyIncomeL3("3","3500-5000","monthlyIncome"),
    monthlyIncomeL4("4","5000-8000","monthlyIncome"),
    monthlyIncomeL5("5","8000以上","monthlyIncome"),
    /**
     * 工资发放
     */
    PAYCASH("1","全部现金","payoff"),
    PAYBYCARD("2","全部银行卡","payoff"),
    HALFCASH("3","部分现金部分银行卡","payoff"),
    /**
     * 有无
     */
    HAVE("1","有","haveOrNot"),
    NOTHAVE("0","无","haveOrNot"),
    /**
     * 性别
     */
    MAN("1","男","sex"),
    WOMAN("0","女","sex"),
    /**
     * 贷款类型
     */
    HOUSE_MORTGAGE("1","房屋抵押","borrowingtype"),
    CAR_MORTGAGE("2","车辆抵押","borrowingtype"),
    CREDICT_MORTGAGE("3","信用抵押","borrowingtype"),
    /**
     * 房屋抵押方式
     */
    ONCE("1","一押","houseMortgage"),
    TWICE("2","二押","houseMortgage"),
    /**
     * 车辆抵押方式
     */
    CAR_LICENCE("2","本","vehicleMortgage"),
    ONLYCAR("2","车","vehicleMortgage"),
    CARANDLICENCE("3","车+本","vehicleMortgage"),
    /**
     * 出场时间
     */
    MINMINUTESL1("1","0-5年","minminutes"),
    MINMINUTESL2("2","5-10年","minminutes"),
    MINMINUTESL3("3","10年以上","minminutes"),
    /**
     * 房屋性质
     */
    HOUSETYPE1("1","住宅","housetype"),
    HOUSETYPE2("2","商用","housetype"),
    HOUSETYPE3("3","商住两用","housetype"),
    HOUSETYPE4("4","别墅","housetype"),
    HOUSETYPE5("5","小产权","housetype"),
    HOUSETYPE6("6","厂房","housetype"),
    /**
     * 房屋状态
     */
    HOUSESTATUS1("1","按揭（以还期数）","housestatus"),
    HOUSESTATUS2("2","持证抵押","housestatus"),
    HOUSESTATUS3("3","二押","housestatus"),
    HOUSESTATUS4("4","全款","housestatus"),
    /**
     *年缴费次数
     */
    YEARSPAYTIMEL1("1","1次","yearspaytime"),
    YEARSPAYTIMEL2("2","2次","yearspaytime"),
    YEARSPAYTIMEL3("3","3-5次","yearspaytime"),
    YEARSPAYTIMEL4("4","5次以上","yearspaytime"),
    YEARSPAYTIMEL5("5","趸交","yearspaytime"),
    /**
     * 保单类型
     */
    GUARANTEETYPE1("1","健康险","guaranteetype"),
    GUARANTEETYPE2("2","理财险","guaranteetype"),
    GUARANTEETYPE3("3","万能险","guaranteetype"),
    /**
     * 甩单类型
     */
    JILTTYPE1("1","可换可甩","jilttype"),
    JILTTYPE2("2","只甩","jilttype"),
    JILTTYPE3("3","只换","jilttype")
    ;
    private String value;
    private String lable;
    private String type;

    DictEnum(String value, String lable, String type) {
        this.value = value;
        this.lable = lable;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
