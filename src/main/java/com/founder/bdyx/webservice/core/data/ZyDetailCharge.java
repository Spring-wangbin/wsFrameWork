package com.founder.bdyx.webservice.core.data;

import java.util.Date;

/**
 * zy_detail_charge(病人住院费用明细)
 */
public class ZyDetailCharge extends VoObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String patientId;// 病人ID码,PK
	private Short admissTimes;// 住院次数,PK
	private Integer ledgerSn;// 结帐次数,PK
	private Integer detailSn;// 流水号,PK
	private Date occTime;// 申请时间
	private Date confirmTime;// 确认时间
	private Date happenDate;// 发生时间
	private String chargeCode;// 收费代码,zd_charge_item.code
	private String billItemCode;// 帐单码
	private String auditCode;// 分类码
	private Double origPrice;// 原单价
	private Double chargePrice;// 实单价
	private Double chargeAmount;// 数量
	private String chargeGroup;//
	private String applyOpera;// 申请人
	private String confirmOpera;// 确认人
	private String chargeStatus;// 状态
	private String infantFlag;// 婴儿标志
	private String selfFlag;// 自费标志
	private String separateFlag;// 单列标志
	private String supriceFlag;//
	private String drugFlag;// 毒麻标志
	private String emergencyFlag;// 抢救标志
	private String opeFlag;// 手术标志
	private String applyStatus;// 申请状态
	private String paySelf;// 住院确认标志
	private String serial;// 药品序列号
	private String wardSn;// 病房编码
	private String deptSn;// 科室编码
	private Double orderNo;// 医嘱序号
	private String execUnit;// 执行科室
	private String groupNo;// 药品库号
	private Integer pageNo;// 页号
	private Short pageType;// 单据类型
	private Date reportDate;//
	private String adtDeptNo;
	private String backFlag;
	private String origDept;
	private String origWard;
	private Date cashDate;
	private Date accountDate;
	private String doctorCode;
	private String sampBarcode;
	private Integer occPageNo;
	private String fitFlag;
	private String sampType;
	private String sampId;
	private String execOpid;
	private Date execTime;
	private String execStatus;
	private Double chargeFee;
	private Integer parentDetailSn;
	private String drawer;
	private String inSource;
	private Double inAmount;
	private Date inDate;
	private String flag;

	private String newType;
	private String backReason; // CCJDEB用

	private String empname;
	private String orgname;
	private String chargename;
	private String chargeUnit;
	private String pageNoName;
	private String wardName;
	private String groupNoName;
	private String patName;
	private String sex;
	private String bedNo;
	private String exIsExcessOweLimt; // 是否欠费已超限额
	private String patientNo;
	private String ageTxt;
	private Double balance;
	private String operaName;
	private String exeOrderName;// M_BUG 0029798
	private Integer writeoffDetailSn;

	private String applyDoctor;
	private String applyDept;
	private String ybLimitFlag;//医保限制性用药标志

// Constructors

	public String getYbLimitFlag() {
		return ybLimitFlag;
	}

	public void setYbLimitFlag(String ybLimitFlag) {
		this.ybLimitFlag = ybLimitFlag;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getPatientNo() {
		return patientNo;
	}

	public void setPatientNo(String patientNo) {
		this.patientNo = patientNo;
	}

	/**
	 * @return the bedNo
	 */
	public String getBedNo() {
		return bedNo;
	}

	/**
	 * @param bedNo
	 *            the bedNo to set
	 */
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	/**
	 * @return the patName
	 */
	public String getPatName() {
		return patName;
	}

	/**
	 * @param patName
	 *            the patName to set
	 */
	public void setPatName(String patName) {
		this.patName = patName;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the wardName
	 */
	public String getWardName() {
		return wardName;
	}

	/**
	 * @param wardName
	 *            the wardName to set
	 */
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	/**
	 * @return the pageNoName
	 */
	public String getPageNoName() {
		return pageNoName;
	}

	/**
	 * @param pageNoName
	 *            the pageNoName to set
	 */
	public void setPageNoName(String pageNoName) {
		this.pageNoName = pageNoName;
	}

	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Double getInAmount() {
		return inAmount;
	}

	public void setInAmount(Double inAmount) {
		this.inAmount = inAmount;
	}

	public String getInSource() {
		return inSource;
	}

	public void setInSource(String inSource) {
		this.inSource = inSource;
	}

	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	public String getChargename() {
		return chargename;
	}

	public void setChargename(String chargename) {
		this.chargename = chargename;
	}

	public String getChargeUnit() {
		return chargeUnit;
	}

	public void setChargeUnit(String chargeUnit) {
		this.chargeUnit = chargeUnit;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	/** default constructor */
	public ZyDetailCharge() {
	}

	public ZyDetailCharge(String patientId, Short admissTimes,
						  Integer ledgerSn, Integer detailSn, Date occTime,
						  String chargeStatus, String applyStatus, String paySelf) {
		this.patientId = patientId;
		this.admissTimes = admissTimes;
		this.ledgerSn = ledgerSn;
		this.detailSn = detailSn;
		this.occTime = occTime;
		this.chargeStatus = chargeStatus;
		this.applyStatus = applyStatus;
		this.paySelf = paySelf;
	}

	/** full constructor */
	public ZyDetailCharge(String patientId, Short admissTimes,
						  Integer ledgerSn, Integer detailSn, Date occTime, Date confirmTime,
						  Date happenDate, String chargeCode, String billItemCode,
						  String auditCode, Double origPrice, Double chargePrice,
						  Double chargeAmount, String chargeGroup, String applyOpera,
						  String confirmOpera, String chargeStatus, String infantFlag,
						  String selfFlag, String separateFlag, String supriceFlag,
						  String drugFlag, String emergencyFlag, String opeFlag,
						  String applyStatus, String paySelf, String serial, String wardSn,
						  String deptSn, Double orderNo, String execUnit, String groupNo,
						  Integer pageNo, Short pageType, Date reportDate, String adtDeptNo,
						  String backFlag, String origDept, String origWard, Date cashDate,
						  Date accountDate, String doctorCode, String sampBarcode,
						  Integer occPageNo, String fitFlag, String sampType, String sampId,
						  String execOpid, Date execTime, String execStatus,
						  Double chargeFee, Integer parentDetailSn) {
		this.patientId = patientId;
		this.admissTimes = admissTimes;
		this.ledgerSn = ledgerSn;
		this.detailSn = detailSn;
		this.occTime = occTime;
		this.confirmTime = confirmTime;
		this.happenDate = happenDate;
		this.chargeCode = chargeCode;
		this.billItemCode = billItemCode;
		this.auditCode = auditCode;
		this.origPrice = origPrice;
		this.chargePrice = chargePrice;
		this.chargeAmount = chargeAmount;
		this.chargeGroup = chargeGroup;
		this.applyOpera = applyOpera;
		this.confirmOpera = confirmOpera;
		this.chargeStatus = chargeStatus;
		this.infantFlag = infantFlag;
		this.selfFlag = selfFlag;
		this.separateFlag = separateFlag;
		this.supriceFlag = supriceFlag;
		this.drugFlag = drugFlag;
		this.emergencyFlag = emergencyFlag;
		this.opeFlag = opeFlag;
		this.applyStatus = applyStatus;
		this.paySelf = paySelf;
		this.serial = serial;
		this.wardSn = wardSn;
		this.deptSn = deptSn;
		this.orderNo = orderNo;
		this.execUnit = execUnit;
		this.groupNo = groupNo;
		this.pageNo = pageNo;
		this.pageType = pageType;
		this.reportDate = reportDate;
		this.adtDeptNo = adtDeptNo;
		this.backFlag = backFlag;
		this.origDept = origDept;
		this.origWard = origWard;
		this.cashDate = cashDate;
		this.accountDate = accountDate;
		this.doctorCode = doctorCode;
		this.sampBarcode = sampBarcode;
		this.occPageNo = occPageNo;
		this.fitFlag = fitFlag;
		this.sampType = sampType;
		this.sampId = sampId;
		this.execOpid = execOpid;
		this.execTime = execTime;
		this.execStatus = execStatus;
		this.chargeFee = chargeFee;
		this.parentDetailSn = parentDetailSn;
	}

	// Property accessors

	public Date getOccTime() {
		return this.occTime;
	}

	public void setOccTime(Date occTime) {
		this.occTime = occTime;
	}

	public Date getConfirmTime() {
		return this.confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getHappenDate() {
		return this.happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	public String getChargeCode() {
		return this.chargeCode;
	}

	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}

	public String getBillItemCode() {
		return this.billItemCode;
	}

	public void setBillItemCode(String billItemCode) {
		this.billItemCode = billItemCode;
	}

	public String getAuditCode() {
		return this.auditCode;
	}

	public void setAuditCode(String auditCode) {
		this.auditCode = auditCode;
	}

	public Double getOrigPrice() {
		return this.origPrice;
	}

	public void setOrigPrice(Double origPrice) {
		this.origPrice = origPrice;
	}

	public Double getChargePrice() {
		return this.chargePrice;
	}

	public void setChargePrice(Double chargePrice) {
		this.chargePrice = chargePrice;
	}

	public Double getChargeAmount() {
		return this.chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeGroup() {
		return this.chargeGroup;
	}

	public void setChargeGroup(String chargeGroup) {
		this.chargeGroup = chargeGroup;
	}

	public String getApplyOpera() {
		return this.applyOpera;
	}

	public void setApplyOpera(String applyOpera) {
		this.applyOpera = applyOpera;
	}

	public String getConfirmOpera() {
		return this.confirmOpera;
	}

	public void setConfirmOpera(String confirmOpera) {
		this.confirmOpera = confirmOpera;
	}

	public String getChargeStatus() {
		return this.chargeStatus;
	}

	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}

	public String getInfantFlag() {
		return this.infantFlag;
	}

	public void setInfantFlag(String infantFlag) {
		this.infantFlag = infantFlag;
	}

	public String getSelfFlag() {
		return this.selfFlag;
	}

	public void setSelfFlag(String selfFlag) {
		this.selfFlag = selfFlag;
	}

	public String getSeparateFlag() {
		return this.separateFlag;
	}

	public void setSeparateFlag(String separateFlag) {
		this.separateFlag = separateFlag;
	}

	public String getSupriceFlag() {
		return this.supriceFlag;
	}

	public void setSupriceFlag(String supriceFlag) {
		this.supriceFlag = supriceFlag;
	}

	public String getDrugFlag() {
		return this.drugFlag;
	}

	public void setDrugFlag(String drugFlag) {
		this.drugFlag = drugFlag;
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

	public String getApplyStatus() {
		return this.applyStatus;
	}

	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	public String getPaySelf() {
		return this.paySelf;
	}

	public void setPaySelf(String paySelf) {
		this.paySelf = paySelf;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
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

	public Double getOrderNo() {
		return this.orderNo;
	}

	public void setOrderNo(Double orderNo) {
		this.orderNo = orderNo;
	}

	public String getExecUnit() {
		return this.execUnit;
	}

	public void setExecUnit(String execUnit) {
		this.execUnit = execUnit;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public Integer getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Short getPageType() {
		return this.pageType;
	}

	public void setPageType(Short pageType) {
		this.pageType = pageType;
	}

	public Date getReportDate() {
		return this.reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getAdtDeptNo() {
		return this.adtDeptNo;
	}

	public void setAdtDeptNo(String adtDeptNo) {
		this.adtDeptNo = adtDeptNo;
	}

	public String getBackFlag() {
		return this.backFlag;
	}

	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}

	public String getOrigDept() {
		return this.origDept;
	}

	public void setOrigDept(String origDept) {
		this.origDept = origDept;
	}

	public String getOrigWard() {
		return this.origWard;
	}

	public void setOrigWard(String origWard) {
		this.origWard = origWard;
	}

	public Date getCashDate() {
		return this.cashDate;
	}

	public void setCashDate(Date cashDate) {
		this.cashDate = cashDate;
	}

	public Date getAccountDate() {
		return this.accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public String getDoctorCode() {
		return this.doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getSampBarcode() {
		return this.sampBarcode;
	}

	public void setSampBarcode(String sampBarcode) {
		this.sampBarcode = sampBarcode;
	}

	public Integer getOccPageNo() {
		return this.occPageNo;
	}

	public void setOccPageNo(Integer occPageNo) {
		this.occPageNo = occPageNo;
	}

	public String getFitFlag() {
		return this.fitFlag;
	}

	public void setFitFlag(String fitFlag) {
		this.fitFlag = fitFlag;
	}

	public String getSampType() {
		return this.sampType;
	}

	public void setSampType(String sampType) {
		this.sampType = sampType;
	}

	public String getSampId() {
		return this.sampId;
	}

	public void setSampId(String sampId) {
		this.sampId = sampId;
	}

	public String getExecOpid() {
		return this.execOpid;
	}

	public void setExecOpid(String execOpid) {
		this.execOpid = execOpid;
	}

	public Date getExecTime() {
		return this.execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public String getExecStatus() {
		return this.execStatus;
	}

	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}

	public Double getChargeFee() {
		return this.chargeFee;
	}

	public void setChargeFee(Double chargeFee) {
		this.chargeFee = chargeFee;
	}

	public Integer getParentDetailSn() {
		return this.parentDetailSn;
	}

	public void setParentDetailSn(Integer parentDetailSn) {
		this.parentDetailSn = parentDetailSn;
	}

	public Short getAdmissTimes() {
		return admissTimes;
	}

	public void setAdmissTimes(Short admissTimes) {
		this.admissTimes = admissTimes;
	}

	public Integer getDetailSn() {
		return detailSn;
	}

	public void setDetailSn(Integer detailSn) {
		this.detailSn = detailSn;
	}

	public Integer getLedgerSn() {
		return ledgerSn;
	}

	public void setLedgerSn(Integer ledgerSn) {
		this.ledgerSn = ledgerSn;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ZyDetailCharge))
			return false;
		ZyDetailCharge castOther = (ZyDetailCharge) other;

		return ((this.getPatientId() == castOther.getPatientId()) || (this
				.getPatientId() != null
				&& castOther.getPatientId() != null && this.getPatientId()
				.equals(castOther.getPatientId())))
				&& ((this.getAdmissTimes() == castOther.getAdmissTimes()) || (this
				.getAdmissTimes() != null
				&& castOther.getAdmissTimes() != null && this
				.getAdmissTimes().equals(castOther.getAdmissTimes())))
				&& ((this.getLedgerSn() == castOther.getLedgerSn()) || (this
				.getLedgerSn() != null
				&& castOther.getLedgerSn() != null && this
				.getLedgerSn().equals(castOther.getLedgerSn())))
				&& ((this.getDetailSn() == castOther.getDetailSn()) || (this
				.getDetailSn() != null
				&& castOther.getDetailSn() != null && this
				.getDetailSn().equals(castOther.getDetailSn())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPatientId() == null ? 0 : this.getPatientId().hashCode());
		result = 37
				* result
				+ (getAdmissTimes() == null ? 0 : this.getAdmissTimes()
				.hashCode());
		result = 37 * result
				+ (getLedgerSn() == null ? 0 : this.getLedgerSn().hashCode());
		result = 37 * result
				+ (getDetailSn() == null ? 0 : this.getDetailSn().hashCode());
		return result;
	}

	/**
	 * @return the groupNoName
	 */
	public String getGroupNoName() {
		return groupNoName;
	}

	/**
	 * @param groupNoName
	 *            the groupNoName to set
	 */
	public void setGroupNoName(String groupNoName) {
		this.groupNoName = groupNoName;
	}

	public String getExIsExcessOweLimt() {
		return exIsExcessOweLimt;
	}

	public void setExIsExcessOweLimt(String exIsExcessOweLimt) {
		this.exIsExcessOweLimt = exIsExcessOweLimt;
	}

	public String getAgeTxt() {
		return ageTxt;
	}

	public void setAgeTxt(String ageTxt) {
		this.ageTxt = ageTxt;
	}

	public String getOperaName() {
		return operaName;
	}

	public void setOperaName(String operaName) {
		this.operaName = operaName;
	}

	public String getBackReason() {
		return backReason;
	}

	public void setBackReason(String backReason) {
		this.backReason = backReason;
	}

	public String getExeOrderName() {
		return exeOrderName;
	}

	public void setExeOrderName(String exeOrderName) {
		this.exeOrderName = exeOrderName;
	}
	public Integer getWriteoffDetailSn() {
		return writeoffDetailSn;
	}

	public void setWriteoffDetailSn(Integer writeoffDetailSn) {
		this.writeoffDetailSn = writeoffDetailSn;
	}

	public String getApplyDoctor() {
		return applyDoctor;
	}

	public void setApplyDoctor(String applyDoctor) {
		this.applyDoctor = applyDoctor;
	}

	public String getApplyDept() {
		return applyDept;
	}

	public void setApplyDept(String applyDept) {
		this.applyDept = applyDept;
	}

}