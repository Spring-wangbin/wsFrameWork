package com.founder.bdyx.task.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2020年1月3日 下午2:50:06
*/
//TODO:测试参考代码
@Table(name = "cis_obs")
public class CisObs implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(insertable=false)
	private Integer apply_no;
	private String order_name;
	
	private Integer charge_status;
	
	private Integer sync_flag;

	public Integer getApply_no() {
		return apply_no;
	}

	public void setApply_no(Integer apply_no) {
		this.apply_no = apply_no;
	}

	public String getOrder_name() {
		return order_name;
	}

	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}

	public Integer getCharge_status() {
		return charge_status;
	}

	public void setCharge_status(Integer charge_status) {
		this.charge_status = charge_status;
	}

	public Integer getSync_flag() {
		return sync_flag;
	}

	public void setSync_flag(Integer sync_flag) {
		this.sync_flag = sync_flag;
	}
	
}
