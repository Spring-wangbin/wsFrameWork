package com.founder.bdyx.task.domain;

import java.util.Date;

/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2020年1月2日 下午3:23:00
*/
public class QueryLogMode  {

	
	private Date queryDate;
	
	private int deleteFlag;

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
}
