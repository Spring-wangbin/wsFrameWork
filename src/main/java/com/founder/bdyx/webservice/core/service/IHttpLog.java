package com.founder.bdyx.webservice.core.service;

import javax.servlet.http.HttpServletRequest;

/**
* @Description HTTP服务调用日志记入
* @author yang.xuefeng
* @version 创建时间：2019年12月26日 下午1:10:29
*/
public interface IHttpLog {

	public int log(long startTime, String requestContent, HttpServletRequest request, String response);

	public int log_webservice(long startTime, String requestContent, String func_id, String retCode, String response);
}
