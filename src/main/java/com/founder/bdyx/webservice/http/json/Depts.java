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

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.service.IHttpLog;
import com.founder.bdyx.webservice.core.service.IWsService;
import com.founder.bdyx.webservice.http.json.util.JsonUtil4Http;

import io.swagger.annotations.Api;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Api(value ="deptsjson", tags = {"获取部门列表servlet"}, produces="application/json")
@WebServlet(name = "deptsjson", urlPatterns = "/deptsjson")
public class Depts extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger _log = Logger.getLogger(Depts.class);
	
    @Autowired
    IWsService wsService;
    
    @Autowired 
    IHttpLog httpLog;
    
	/**
	 * Constructor of the object.
	 */
	public Depts() {
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
		_log.info("服务请求Json入参sRequest:" + sRequest);

		JsonUtil4Http jsonUtil = null;
		String  invokeResult = null;
		try {
            _log.info("初始化入参Json对象");
			jsonUtil = new JsonUtil4Http();
			jsonUtil.getInputJson(sRequest);
			String strSql = "SET NOCOUNT ON  exec jyt_depts_json ";
			strSql = strSql + "'" + (jsonUtil.jsongObjReq.getString("hos_code")) + "'";
			
			List<Map<String,Object>> result = wsService.execProce4ListMap(strSql);
			_log.info("执行SQL返回结果：" + JSON.toJSONString(result));
			if (result == null || CollectionUtils.isEmpty(result)){
				_log.info("执行SQL返回NULL，请检查！");
				jsonUtil.setRetMsg(WsConstant.MSG_CODE_1014);
				_log.warn(invokeResult);
			}
			
			// 返回对象
			jsonUtil.getJsonResult(result);
			invokeResult = jsonUtil.getJsonStrRes();
			
		} catch (JedisConnectionException e) {
			e.printStackTrace();
			_log.error("redis 连接异常： " + e.getMessage());
			invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1017, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            _log.error("系统发生了异常！" + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1010, e.getMessage());
        }
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		
		PrintWriter out = response.getWriter();
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
