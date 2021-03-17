package com.founder.bdyx.webservice.http.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.jws.WebParam;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.service.IHttpLog;
import com.founder.bdyx.webservice.core.service.IWsService;
import com.founder.bdyx.webservice.http.xml.util.XmlUtil4Http;

import io.swagger.annotations.Api;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Api(value ="Doctors", tags = {"获取部门列表servlet"}, produces="application/xml")
@WebServlet(name = "doctors", urlPatterns = "/doctors")
public class Doctors extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger _log = Logger.getLogger(Doctors.class);
	
    @Autowired
    IWsService wsService;
    
    @Autowired 
    IHttpLog httpLog;
    
	/**
	 * Constructor of the object.
	 */
	public Doctors() {
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
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		String sRequest = sb.toString().trim();
		_log.info("服务请求XML入参sRequest:" + sRequest);

		XmlUtil4Http xmlUtil = null;
		String  invokeResult = null;
		try {
			xmlUtil = new XmlUtil4Http();
			xmlUtil.InitInput(sRequest);
			String strSql = "SET NOCOUNT ON  exec jyt_Doctors ";
			strSql = strSql + "'" + (xmlUtil.inputMap.get("hos_code").trim()) + "'";

			List<String> result = wsService.execProc4ListString(strSql);
			_log.info("执行SQL返回结果：" + JSON.toJSONString(result));
			if (result == null || CollectionUtils.isEmpty(result)){
				_log.info("执行SQL返回NULL，请检查！");
				xmlUtil.setRetMsg(WsConstant.MSG_CODE_1014);
				_log.warn(invokeResult);
			}
			xmlUtil.sSqlResult = result.get(0);
			xmlUtil.prepareOutNodes();
			invokeResult = xmlUtil.getOut();
		} catch (TransformerConfigurationException e) {
    		e.printStackTrace();
    		_log.error("输出XML转换发生了异常！" + e.getMessage());
    		invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1016, e.getMessage());
    		
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            _log.error("redis 连接异常： " + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1017, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            _log.error("系统发生了异常！" + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1010, e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            xmlUtil.setRetMsg(WsConstant.MSG_CODE_1020, e.getMessage());
		}
		
		response.setContentType("text/xml;charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = response.getWriter();
//		sResult = "<result></result>";
		out.println(invokeResult);
		_log.info("输出结果:" +invokeResult);
		
		out.flush();
		out.close();
		
		// 记入日志
		httpLog.log(startTime, sRequest, request, invokeResult);
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
