package com.founder.bdyx.webservice.http.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrConfig;
import com.founder.bdyx.webservice.core.service.IHttpLog;
import com.founder.bdyx.webservice.core.service.IWsService;
import com.founder.bdyx.webservice.http.json.util.JsonUtil4Http;
import com.founder.bdyx.webservice.soap.json.util.JsonUtil4Soap;

import io.swagger.annotations.Api;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 
 * @author yang.xuefeng
 *
 */
@Api(value ="jsonHttpServlet", tags = {"共通处理servlet"}, produces="application/json")
@WebServlet(name = "jsonHttpServlet", urlPatterns = "/ws/json")
public class JsonHttpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(JsonHttpServlet.class);
	
    @Autowired
    IWsService wsService;
    
    @Autowired 
    IHttpLog httpLog;
    
	/**
	 * Constructor of the object.
	 */
	public JsonHttpServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(@WebParam(name = "request") HttpServletRequest request, @WebParam(name = "response") HttpServletResponse response)
			throws ServletException, IOException {

		long startTime = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new  InputStreamReader(request.getInputStream(), "utf-8"));
		String line = null;
		StringBuilder strb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			strb.append(line);
		}

		String sRequest = strb.toString().trim();
		_log.info("服务请求Json入参sRequest:" + sRequest);

		JsonUtil4Http jsonUtil = null;
		String  invokeResult = null;
		try {
			// 请求参数读入
			jsonUtil = new JsonUtil4Http();
			jsonUtil.getInputJson(sRequest);

			// 获取通用方法
            String nameMethod = jsonUtil.jsongObjReq.getString(JsonUtil4Soap.Method_Code);
            _log.info("业务编码：{}", nameMethod);
            if (StringUtils.isEmpty(nameMethod)){
                _log.error("业务编码不允许为空");
                jsonUtil.setRetMsg(WsConstant.MSG_CODE_1060);
                _log.warn(jsonUtil.getJsonStrRes());
                
        		outPrint (invokeResult, response);
        		// 记入日志
        		httpLog.log(startTime, sRequest, request, invokeResult);
        		return;
            }
            
            List<WebsvrConfig> list = wsService.getConfig(nameMethod);
            _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
            if(CollectionUtils.isEmpty(list)) {
                _log.error("没有匹配到记录,请配置表f_websvr_config。");
                jsonUtil.setRetMsg(WsConstant.MSG_CODE_1013);
                invokeResult = jsonUtil.getJsonStrRes();
        		outPrint (invokeResult, response);
        		// 记入日志
        		httpLog.log(startTime, sRequest, request, invokeResult);
        		return;
            }

            WebsvrConfig websvrConfig = list.get(0);
            String funcName = websvrConfig.getFunc_name();
            String parmasList = websvrConfig.getParmas_list();
            String invokeProc = websvrConfig.getInvoke_proc();
            Integer invokeType = websvrConfig.getInvoke_type();
            _log.info("功能名称：{}", funcName);
            _log.info("参数定义：{}", parmasList);
            _log.info("功能类型：" + invokeType + "(0、代码 1、配置)");
            _log.info("调用过程：" + invokeProc);
            if (invokeType != 1){
                _log.error("没有匹配到记录,请配置表f_websvr_config.参数invokeType不为1.");
				jsonUtil.setRetMsg(WsConstant.MSG_CODE_1015);
				invokeResult = jsonUtil.getJsonStrRes();
        		outPrint (invokeResult, response);
        		// 记入日志
        		httpLog.log(startTime, sRequest, request, invokeResult);
        		return;
        	}
        	
        	String[] _abc = parmasList.split("\\|");
        	StringBuilder sb = new StringBuilder();
        	for (int i = 0; i < _abc.length; i++) {
        		String _a = _abc[i];
        		String _b = (String) jsonUtil.jsongObjReq.get(_a); //jsonUtil.inputMap.get(_a);
        		String value = StringUtils.hasText(_b) ? _b : "";
        		_log.debug("参数" + _a + ":" + value);
        		value = "'" + value.replaceAll("\\|", ",") + "'";
        		sb.append(value);
        		if (i < _abc.length - 1) {
        			sb.append(",");
        		}
        	}
            String invokeParms = sb.toString();
            _log.info("参数值：" + invokeParms);
            String strSql = "EXEC " + invokeProc + " " + invokeParms;
            _log.info("拼装完整SQL执行语句：" + strSql);

            List<Map<String,Object>> result = wsService.execProce4ListMap(strSql);
            _log.info("执行SQL返回结果：" + JSON.toJSONString(result));
            if (result == null || CollectionUtils.isEmpty(result)){
            	_log.info("执行SQL返回NULL，请检查！");
            	jsonUtil.setRetMsg(WsConstant.MSG_CODE_1014);
            	invokeResult = jsonUtil.getJsonStrRes();
        		outPrint (invokeResult, response);
        		// 记入日志
        		httpLog.log(startTime, sRequest, request, invokeResult);
        		return;
        	}
            // 返回对象
			jsonUtil.getJsonResult(result);
			invokeResult = jsonUtil.getJsonStrRes();
			
	    } catch (JedisConnectionException e) {
	        e.printStackTrace();
	        _log.error("redis 连接异常： " + e.getMessage());
	        jsonUtil.setRetMsg(WsConstant.MSG_CODE_1017, e.getLocalizedMessage());
	        invokeResult = jsonUtil.getJsonStrRes();
	    } catch (Exception e) {
	        e.printStackTrace();
	        jsonUtil.setRetMsg(WsConstant.MSG_CODE_1010, e.getLocalizedMessage());
	        _log.warn(jsonUtil.getJsonStrRes());
	        invokeResult = jsonUtil.getJsonStrRes();
	    }
    
		outPrint (invokeResult, response);
		// 记入日志
		httpLog.log(startTime, sRequest, request, invokeResult);
	}

	
	/**
	 * 处理输出
	 * @param invokeResult
	 * @param response
	 */
	private void outPrint(String invokeResult, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml;charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = response.getWriter();
//		sResult = "<result></result>";
		out.println(invokeResult);
		_log.info("输出结果:" +invokeResult);
		
		out.flush();
		out.close();
	}
	
	
	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
