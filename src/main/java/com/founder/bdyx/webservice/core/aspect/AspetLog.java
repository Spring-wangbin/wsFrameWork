package com.founder.bdyx.webservice.core.aspect;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.founder.bdyx.webservice.soap.json.util.JsonUtil4Soap;

/**
* @Description 日志切面处理
* @author yang.xuefeng
* @version 创建时间：2019年12月20日 下午3:33:00
*/
@Aspect
@Order(-999)
@Component
public class AspetLog {
	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(AspetLog.class);

    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    private ThreadLocal<Future<WebsvrLog>> logThreadLocal = new ThreadLocal<Future<WebsvrLog>>();

    @Autowired
    LogAsync logAsync;
    
    @Pointcut("execution(public * com.founder.bdyx.webservice.soap.*.itf..*.*(..))")
    public void doBusiness() {
        _log.info("doBusiness");
    }

    @Before("doBusiness()")
    public void doBeforeSoapXml(JoinPoint joinPoint){
        _log.info("doBeforeSoapXml");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String clientIp = request.getRemoteAddr();
        _log.info("clientIp : " + clientIp);
        String requestXml = (String) joinPoint.getArgs()[0];
        _log.info("requestXml : " + requestXml);
        
        WebsvrLog websvrLog = new WebsvrLog();
        websvrLog.setHappen_date(new Date());
        websvrLog.setClient_ip(clientIp);
        websvrLog.setRequest_xml(requestXml);
        
		//请求的方法名
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		websvrLog.setRequest_method(className + "." + methodName + "()");
		
        Future<WebsvrLog> logFuture = logAsync.saveLog(websvrLog);
        logThreadLocal.set(logFuture);
    }

    @AfterReturning(returning = "ret", pointcut = "doBusiness()")
    public void afterReturnSoapXml(Object ret){
        _log.info("afterReturnSoapXml");
        Future<WebsvrLog> logFuture = logThreadLocal.get();
        try {
            WebsvrLog websvrLog = logFuture.get();
            if (ret != null && websvrLog != null){
                websvrLog.setResponse_xml((String) ret);
                websvrLog.setResonse_date(new Date());
                
                if (ret.toString().contains(WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS))) {
                	websvrLog.setExecute_flag(WsConstant.MSG_CODE_SUCESS);
                	websvrLog.setExecute_message(WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS));
                } else {
                	websvrLog.setExecute_flag(WsConstant.MSG_CODE_FAIL);
                	if (ret.toString().contains(WsConstant.msgMap.get(WsConstant.MSG_CODE_FAIL))) {
                	   websvrLog.setExecute_message(WsConstant.msgMap.get(WsConstant.MSG_CODE_FAIL));
                	} else if (ret.toString().contains(WsConstant.MESSAGE)) {
                        JsonUtil4Soap jsonUtil = new JsonUtil4Soap();
                        jsonUtil.getInputJson(ret.toString());
                        websvrLog.setExecute_message(jsonUtil.jsongObjReq.getString(WsConstant.MESSAGE));
                	} else if (ret.toString().contains(WsConstant.RETURN_MSG)) {
                		String errorMsg = ret.toString().substring(ret.toString().indexOf("<" + WsConstant.RETURN_MSG), ret.toString().indexOf("</" + WsConstant.RETURN_MSG));
                        websvrLog.setExecute_message(errorMsg);
                	}
                }
                
        		//执行时长(毫秒)
        		long els_time = System.currentTimeMillis() - websvrLog.getHappen_date().getTime();
        		websvrLog.setEls_time(els_time);
                logFuture = logAsync.updateLog(websvrLog);
                logThreadLocal.set(logFuture);
                _log.info("更新对象：" + JSON.toJSONString(websvrLog));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
    }
    
    public ThreadLocal<Long> getStartTime() {
        return startTime;
    }

    public void setStartTime(ThreadLocal<Long> startTime) {
        this.startTime = startTime;
    }

    public ThreadLocal<Future<WebsvrLog>> getLogThreadLocal() {
        return logThreadLocal;
    }

    public void setLogThreadLocal(ThreadLocal<Future<WebsvrLog>> logThreadLocal) {
        this.logThreadLocal = logThreadLocal;
    }
}
