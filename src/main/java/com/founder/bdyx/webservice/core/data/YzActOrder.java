package com.founder.bdyx.webservice.core.data;

import java.util.Date;


/**
 * yz_act_order(在院病人医嘱表)
 */
public class YzActOrder extends VoObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Double actOrderNo;// 序列号,PK
	private String orderCode;// 医嘱码,yz_order_item
	private String patientId;// 机制病历号
	private short admissTimes;// 住院次数
	private String serial;// 药品序列号
	private String orderName;// 医嘱名称
	private Date orderTime;// 下医嘱时间
	private Date enterTime;// 录入时间
	private Date confirmTime;// 确认时间
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Date endTime2;// 结束时间
	private Date performanceTime;// 执行时间
	private String orderDoctor;// 下医嘱医生,a_employee_mi.emp_sn
	private String stopDoctor;// 停医嘱医生,a_employee_mi.emp_sn
	private String enterOper;// 录入人,a_employee_mi.emp_sn
	private String modifier;// 撤消人
	private String confirmOpera;// 确认人,a_employee_mi.emp_sn
	private String stopOpera;// 停止人,a_employee_mi.emp_sn
	private String stopConfirmOpera;// 停止确认人,a_employee_mi.emp_sn
	private String frequCode;// 频率码,yz_frequence
	private String orderType;// 医嘱类型,yz_order_type
	private String statusFlag;// 医嘱状态[0:录入; 1:发送; 3,4:执行;5:停止][老版中1:录入]
	private String supplyCode;// 给药方式,yz_supply
	private String drugSpecification;// 药品规格,yp_dict.specification
	private Double doseage;// 剂量
	private String doseageUnit;// 剂量单位,yp_dict.dosage_unit
	private Double chargeAmount;// 领用量
	private Double drugOcc;// 药品执行用量
	private String miniUnit;// 最小包装单位,yp_dict.mini_unit
	private String printOrderChange;// 变更
	private float parentNo;// 父医嘱号
	private String printOrder;// 打印标志
	private String exclusiveType;// 排斥类型,yz_order_item.exclusive_type
	private Date excluBackTime;// 排斥时间
	private float excluActOrderNo;// 排斥医嘱号
	private String paySelf;// 住院处确认标志,0 普通 1 需住院确认
	private String groupNo;// 库号,yp_base.group_no
	private String infantFlag;// 婴儿
	private String emergencyFlag;// 抢救
	private String opeFlag;// 手术
	private String selfBuy;// 自备标志1自备2嘱托3数药
	private String instruction;// 嘱托
	private String discription;// 描述
	private String deletedFlag;// 撤销标志[1:撤销]
	private String backFlag;//
	private Double alterPrintOrder;//
	private String execUnit;//
	private String longOnceFlag;//
	private String fitFlag;//
	private String preventiveFlag;//
	private String therapeuticFlag;//
	private String sbEquipId;//
	private String sbLoanDocuNo;//
	private String wardSn;//
	private String deptSn;//
	private String skinTestFlag;//
	private String skinTestResult;//
	private String ybLimitFlag;

	public String getYbLimitFlag() {
		return ybLimitFlag;
	}

	public void setYbLimitFlag(String ybLimitFlag) {
		this.ybLimitFlag = ybLimitFlag;
	}
// Constructors

	/** default constructor */
	public YzActOrder() {
	}

	/** minimal constructor */
	public YzActOrder(Double actOrderNo, String orderCode, String patientId,
					  short admissTimes, String deletedFlag, String longOnceFlag) {
		this.actOrderNo = actOrderNo;
		this.orderCode = orderCode;
		this.patientId = patientId;
		this.admissTimes = admissTimes;
		this.deletedFlag = deletedFlag;
		this.longOnceFlag = longOnceFlag;
	}

	/** full constructor */
	public YzActOrder(Double actOrderNo, String orderCode, String patientId,
					  short admissTimes, String serial, String orderName, Date orderTime,
					  Date enterTime, Date confirmTime, Date startTime, Date endTime,
					  Date endTime2, Date performanceTime, String orderDoctor,
					  String stopDoctor, String enterOper, String modifier,
					  String confirmOpera, String stopOpera, String stopConfirmOpera,
					  String frequCode, String orderType, String statusFlag,
					  String supplyCode, String drugSpecification, Double doseage,
					  String doseageUnit, Double chargeAmount, Double drugOcc,
					  String miniUnit, String printOrderChange, float parentNo,
					  String printOrder, String exclusiveType, Date excluBackTime,
					  float excluActOrderNo, String paySelf, String groupNo,
					  String infantFlag, String emergencyFlag, String opeFlag,
					  String selfBuy, String instruction, String discription,
					  String deletedFlag, String backFlag, Double alterPrintOrder,
					  String execUnit, String longOnceFlag, String fitFlag,
					  String preventiveFlag, String therapeuticFlag, String sbEquipId,
					  String sbLoanDocuNo, String wardSn, String deptSn,
					  String skinTestFlag, String skinTestResult) {
		this.actOrderNo = actOrderNo;
		this.orderCode = orderCode;
		this.patientId = patientId;
		this.admissTimes = admissTimes;
		this.serial = serial;
		this.orderName = orderName;
		this.orderTime = orderTime;
		this.enterTime = enterTime;
		this.confirmTime = confirmTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.endTime2 = endTime2;
		this.performanceTime = performanceTime;
		this.orderDoctor = orderDoctor;
		this.stopDoctor = stopDoctor;
		this.enterOper = enterOper;
		this.modifier = modifier;
		this.confirmOpera = confirmOpera;
		this.stopOpera = stopOpera;
		this.stopConfirmOpera = stopConfirmOpera;
		this.frequCode = frequCode;
		this.orderType = orderType;
		this.statusFlag = statusFlag;
		this.supplyCode = supplyCode;
		this.drugSpecification = drugSpecification;
		this.doseage = doseage;
		this.doseageUnit = doseageUnit;
		this.chargeAmount = chargeAmount;
		this.drugOcc = drugOcc;
		this.miniUnit = miniUnit;
		this.printOrderChange = printOrderChange;
		this.parentNo = parentNo;
		this.printOrder = printOrder;
		this.exclusiveType = exclusiveType;
		this.excluBackTime = excluBackTime;
		this.excluActOrderNo = excluActOrderNo;
		this.paySelf = paySelf;
		this.groupNo = groupNo;
		this.infantFlag = infantFlag;
		this.emergencyFlag = emergencyFlag;
		this.opeFlag = opeFlag;
		this.selfBuy = selfBuy;
		this.instruction = instruction;
		this.discription = discription;
		this.deletedFlag = deletedFlag;
		this.backFlag = backFlag;
		this.alterPrintOrder = alterPrintOrder;
		this.execUnit = execUnit;
		this.longOnceFlag = longOnceFlag;
		this.fitFlag = fitFlag;
		this.preventiveFlag = preventiveFlag;
		this.therapeuticFlag = therapeuticFlag;
		this.sbEquipId = sbEquipId;
		this.sbLoanDocuNo = sbLoanDocuNo;
		this.wardSn = wardSn;
		this.deptSn = deptSn;
		this.skinTestFlag = skinTestFlag;
		this.skinTestResult = skinTestResult;
	}

	// Property accessors

	public String getPatientId() {
		return this.patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public short getAdmissTimes() {
		return this.admissTimes;
	}

	public void setAdmissTimes(short admissTimes) {
		this.admissTimes = admissTimes;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOrderName() {
		return this.orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getEnterTime() {
		return this.enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime2() {
		return this.endTime2;
	}

	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	public Date getPerformanceTime() {
		return this.performanceTime;
	}

	public void setPerformanceTime(Date performanceTime) {
		this.performanceTime = performanceTime;
	}

	public String getOrderDoctor() {
		return this.orderDoctor;
	}

	public void setOrderDoctor(String orderDoctor) {
		this.orderDoctor = orderDoctor;
	}

	public String getStopDoctor() {
		return this.stopDoctor;
	}

	public void setStopDoctor(String stopDoctor) {
		this.stopDoctor = stopDoctor;
	}

	public String getEnterOper() {
		return this.enterOper;
	}

	public void setEnterOper(String enterOper) {
		this.enterOper = enterOper;
	}

	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getConfirmOpera() {
		return this.confirmOpera;
	}

	public void setConfirmOpera(String confirmOpera) {
		this.confirmOpera = confirmOpera;
	}

	public String getStopOpera() {
		return this.stopOpera;
	}

	public void setStopOpera(String stopOpera) {
		this.stopOpera = stopOpera;
	}

	public String getStopConfirmOpera() {
		return this.stopConfirmOpera;
	}

	public void setStopConfirmOpera(String stopConfirmOpera) {
		this.stopConfirmOpera = stopConfirmOpera;
	}

	public String getFrequCode() {
		return this.frequCode;
	}

	public void setFrequCode(String frequCode) {
		this.frequCode = frequCode;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getStatusFlag() {
		return this.statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getSupplyCode() {
		return this.supplyCode;
	}

	public void setSupplyCode(String supplyCode) {
		this.supplyCode = supplyCode;
	}

	public String getDrugSpecification() {
		return this.drugSpecification;
	}

	public void setDrugSpecification(String drugSpecification) {
		this.drugSpecification = drugSpecification;
	}

	public Double getDoseage() {
		return this.doseage;
	}

	public void setDoseage(Double doseage) {
		this.doseage = doseage;
	}

	public String getDoseageUnit() {
		return this.doseageUnit;
	}

	public void setDoseageUnit(String doseageUnit) {
		this.doseageUnit = doseageUnit;
	}

	public Double getChargeAmount(Object chargeAmount) {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public Double getDrugOcc() {
		return this.drugOcc;
	}

	public void setDrugOcc(Double drugOcc) {
		this.drugOcc = drugOcc;
	}

	public String getMiniUnit() {
		return this.miniUnit;
	}

	public void setMiniUnit(String miniUnit) {
		this.miniUnit = miniUnit;
	}

	public String getPrintOrderChange() {
		return this.printOrderChange;
	}

	public void setPrintOrderChange(String printOrderChange) {
		this.printOrderChange = printOrderChange;
	}

	public float getParentNo() {
		return this.parentNo;
	}

	public void setParentNo(float parentNo) {
		this.parentNo = parentNo;
	}

	public String getPrintOrder() {
		return this.printOrder;
	}

	public void setPrintOrder(String printOrder) {
		this.printOrder = printOrder;
	}

	public String getExclusiveType() {
		return this.exclusiveType;
	}

	public void setExclusiveType(String exclusiveType) {
		this.exclusiveType = exclusiveType;
	}

	public Date getExcluBackTime() {
		return this.excluBackTime;
	}

	public void setExcluBackTime(Date excluBackTime) {
		this.excluBackTime = excluBackTime;
	}

	public float getExcluActOrderNo() {
		return this.excluActOrderNo;
	}

	public void setExcluActOrderNo(float excluActOrderNo) {
		this.excluActOrderNo = excluActOrderNo;
	}

	public String getPaySelf() {
		return this.paySelf;
	}

	public void setPaySelf(String paySelf) {
		this.paySelf = paySelf;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getInfantFlag() {
		return this.infantFlag;
	}

	public void setInfantFlag(String infantFlag) {
		this.infantFlag = infantFlag;
	}

	public String getEmergencyFlag() {
		return this.emergencyFlag;
	}

	public void setEmergencyFlag(String emergencyFlag) {
		this.emergencyFlag = emergencyFlag;
	}

	public String getOpeFlag() {
		return this.opeFlag;
	}

	public void setOpeFlag(String opeFlag) {
		this.opeFlag = opeFlag;
	}

	public String getSelfBuy() {
		return this.selfBuy;
	}

	public void setSelfBuy(String selfBuy) {
		this.selfBuy = selfBuy;
	}

	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public String getDiscription() {
		return this.discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getDeletedFlag() {
		return this.deletedFlag;
	}

	public void setDeletedFlag(String deletedFlag) {
		this.deletedFlag = deletedFlag;
	}

	public String getBackFlag() {
		return this.backFlag;
	}

	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}

	public Double getAlterPrintOrder() {
		return this.alterPrintOrder;
	}

	public void setAlterPrintOrder(Double alterPrintOrder) {
		this.alterPrintOrder = alterPrintOrder;
	}

	public String getExecUnit() {
		return this.execUnit;
	}

	public void setExecUnit(String execUnit) {
		this.execUnit = execUnit;
	}

	public String getLongOnceFlag() {
		return this.longOnceFlag;
	}

	public void setLongOnceFlag(String longOnceFlag) {
		this.longOnceFlag = longOnceFlag;
	}

	public String getFitFlag() {
		return this.fitFlag;
	}

	public void setFitFlag(String fitFlag) {
		this.fitFlag = fitFlag;
	}

	public String getPreventiveFlag() {
		return this.preventiveFlag;
	}

	public void setPreventiveFlag(String preventiveFlag) {
		this.preventiveFlag = preventiveFlag;
	}

	public String getTherapeuticFlag() {
		return this.therapeuticFlag;
	}

	public void setTherapeuticFlag(String therapeuticFlag) {
		this.therapeuticFlag = therapeuticFlag;
	}

	public String getSbEquipId() {
		return this.sbEquipId;
	}

	public void setSbEquipId(String sbEquipId) {
		this.sbEquipId = sbEquipId;
	}

	public String getSbLoanDocuNo() {
		return this.sbLoanDocuNo;
	}

	public void setSbLoanDocuNo(String sbLoanDocuNo) {
		this.sbLoanDocuNo = sbLoanDocuNo;
	}

	public String getWardSn() {
		return this.wardSn;
	}

	public void setWardSn(String wardSn) {
		this.wardSn = wardSn;
	}

	public String getDeptSn() {
		return this.deptSn;
	}

	public void setDeptSn(String deptSn) {
		this.deptSn = deptSn;
	}

	public String getSkinTestFlag() {
		return this.skinTestFlag;
	}

	public void setSkinTestFlag(String skinTestFlag) {
		this.skinTestFlag = skinTestFlag;
	}

	public String getSkinTestResult() {
		return this.skinTestResult;
	}

	public void setSkinTestResult(String skinTestResult) {
		this.skinTestResult = skinTestResult;
	}

	public Double getActOrderNo() {
		return actOrderNo;
	}

	public void setActOrderNo(Double actOrderNo) {
		this.actOrderNo = actOrderNo;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof YzActOrder))
			return false;
		YzActOrder castOther = (YzActOrder) other;

		return (this.getActOrderNo() == castOther.getActOrderNo())
				&& ((this.getOrderCode() == castOther.getOrderCode()) || (this
				.getOrderCode() != null
				&& castOther.getOrderCode() != null && this
				.getOrderCode().equals(castOther.getOrderCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getActOrderNo().intValue();
		result = 37 * result
				+ (getOrderCode() == null ? 0 : this.getOrderCode().hashCode());
		return result;
	}
}