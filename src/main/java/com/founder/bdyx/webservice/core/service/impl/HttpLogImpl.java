package com.founder.bdyx.webservice.core.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.bdyx.webservice.core.aspect.AspetLog;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.founder.bdyx.webservice.core.mapper.WebsvrLogMapper;
import com.founder.bdyx.webservice.core.service.IHttpLog;
import com.founder.bdyx.webservice.soap.json.util.JsonUtil4Soap;
import org.springframework.transaction.annotation.Transactional;

/**
* @Description HTTP服务调用日志记入
* @author yang.xuefeng
* @version 创建时间：2019年12月26日 下午12:56:16
*/
@Service
@Transactional
public class HttpLogImpl implements IHttpLog {

	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(AspetLog.class);
	
    @Autowired
    WebsvrLogMapper websvrLogMapper;
    
    public int log(long startTime, String requestContent, HttpServletRequest request, String response) {

        String clientIp = request.getRemoteAddr();
        _log.info("clientIp : " + clientIp);
        _log.info("requestContent : " + requestContent);

        WebsvrLog websvrLog = new WebsvrLog();
        websvrLog.setHappen_date(new Date(startTime));
        websvrLog.setClient_ip(clientIp);
        websvrLog.setRequest_xml(requestContent);

        //请求的方法名
        websvrLog.setRequest_method(request.getRequestURI() + "." + request.getMethod());
        websvrLog.setResponse_xml(response);

        if (response.contains(WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS))) {
            websvrLog.setExecute_flag(WsConstant.MSG_CODE_SUCESS);
            websvrLog.setExecute_message(WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS));
        } else {
            websvrLog.setExecute_flag(WsConstant.MSG_CODE_FAIL);
            if (response.contains(WsConstant.msgMap.get(WsConstant.MSG_CODE_FAIL))) {
                websvrLog.setExecute_message(WsConstant.msgMap.get(WsConstant.MSG_CODE_FAIL));
            } else if (response.contains(WsConstant.MESSAGE)) {
                JsonUtil4Soap jsonUtil = new JsonUtil4Soap();
                jsonUtil.getInputJson(response);
                websvrLog.setExecute_message(jsonUtil.jsongObjReq.getString(WsConstant.MESSAGE));
            } else if (response.contains(WsConstant.RETURN_MSG)) {
                String errorMsg = response.substring(response.indexOf("<" + WsConstant.RETURN_MSG), response.indexOf("</" + WsConstant.RETURN_MSG));
                websvrLog.setExecute_message(errorMsg);
            }
        }

        long endTime = System.currentTimeMillis();
        websvrLog.setResonse_date(new Date(endTime));
        //执行时长(毫秒)
        websvrLog.setEls_time(endTime - startTime);

        return websvrLogMapper.insertAndGetId(websvrLog);

    }


    public int log_webservice(long startTime, String requestContent, String func_id, String retCode, String response) {

        _log.info("requestContent : " + requestContent);

        String serverIp = "";
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
            _log.info("serverIp : " + serverIp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        WebsvrLog websvrLog = new WebsvrLog();
        websvrLog.setHappen_date(new Date(startTime));
        websvrLog.setClient_ip("webservice");
        websvrLog.setRequest_xml(requestContent);
        //日志增加本地服务器ip字段，便于负载均衡后查看指定日志
        websvrLog.setServer_ip(serverIp);

        //请求的方法名
        websvrLog.setRequest_method(func_id);
        websvrLog.setResponse_xml(response);

        websvrLog.setExecute_flag(retCode);
        websvrLog.setExecute_message(WsConstant.msgMap.get(retCode));

        long endTime = System.currentTimeMillis();
        websvrLog.setResonse_date(new Date(endTime));
        //执行时长(毫秒)
        websvrLog.setEls_time(endTime - startTime);

        return websvrLogMapper.insertAndGetId(websvrLog);

    }
}
