package app.pp.enums;
/***
 *@author 邹宏伟
 *2016-5-26下午13:24:00
 *@Version: 是否置顶
 */
public enum CommonEnum {
	/**
	 * 启用禁用状态
	 */
	StartUsing(1,"启用"),
	Forbidden(2,"禁用"),
	/**
	 * 删除标记
	 */
	Del_Y(1,"已删除"),
	Del_N(0,"正常"),


	OrderState1(1,"补缴"),
	OrderState2(2,"补预缴"),
	OrderState3(3,"续费"),
	OrderState5(5,"正常"),

	/**
	 * 订单记录
	 */
	Order1(1,"新增订单"),
	Order2(2,"认领"),
	Order3(3,"确认资料"),
	Order4(4,"修改备注"),
	Order5(5,"修改资料图片"),
	Order6(6,"外派"),
	Order7(7,"外派完成"),
	Order8(8,"后台结清"),
	Order9(9,"变更渠道"),
	Order11(11,"后台续缴"),
	Order12(12,"备注"),
	Order10(10,"取消订单"),
	Order13(13,"确认订单"),
	Order15(15,"关闭订单"),
	Order16(16,"补缴"),
	Order17(17,"审核关闭订单"),
	Order14(14,"完成订单");



	private CommonEnum(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	private Integer id;
	private String name;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
