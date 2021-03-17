package com.founder.bdyx.webservice.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
* @Description 功能描述 常量类
* @author yang.xuefeng
* @version 创建时间：2019年12月24日 下午1:19:00
*/
public class WsConstant {
	
	// 根据项目接口具体要求、可灵活统一修改下列常量结构体值
	
	/** CODE: code */
	public static String CODE = "code";
	
	/** MESSAGE: message */
	public static String MESSAGE = "message";
	
	/** DATA: data */
	public static String DATA = "data";
	
	
	
	/** RETURN_CODE: returnCode */
	public static String RETURN_CODE = "result";
	
	/** RETURN_MSG: returnMsg */
	public static String RETURN_MSG = "returnMsg";
	
	/** RETURN_DATA: returnData */
	public static String RETURN_DATA = "content";
	
	
	public static String A_FOR_CODE = "AAA_CODE";
	
	public static String A_FOR_MSG = "AAA_MSG";
	
	public static String RETURN_NO_DATA = "no_data";
	
    /*
     * 可随时扩展
     */
    public static String MSG_CODE_SUCESS = "1";
    public static String MSG_CODE_FAIL = "-1";
    public static String MSG_CODE_NO_DATA = "0";
    public static String MSG_CODE_1010 = "1010";
    public static String MSG_CODE_1020 = "1020";
    public static String MSG_CODE_1030 = "1030";
    public static String MSG_CODE_1040 = "1040";
    public static String MSG_CODE_1050 = "1050";
    public static String MSG_CODE_1060 = "1060";
    public static String MSG_CODE_1011 = "1011";
    public static String MSG_CODE_1013 = "1013";
    public static String MSG_CODE_1014 = "1014";
    public static String MSG_CODE_1015 = "1015";
    public static String MSG_CODE_1016 = "1016";
    public static String MSG_CODE_1017 = "1017";
    
	// "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?><response><ret_code>0</ret_code><ret_msg>执行成功</ret_msg><output></output></response>";
	/** XML服务共通返回字符串 默认为该字符串 成功失败都在该字符串上操作*/
	private static StringBuffer errMsg4Xml = new StringBuffer()
			.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>")
			.append("<response>")
			.append("<").append(RETURN_CODE).append(">")
			.append(A_FOR_CODE)
			.append("</").append(RETURN_CODE).append(">")
			.append("<").append(RETURN_DATA).append(">")
			.append(RETURN_NO_DATA)
			.append("</").append(RETURN_DATA).append(">")
			.append("</response>");
	
	/** 请求成功字符串，适用于 XML 暂时不使用*/
	public static StringBuffer RTN_SUCC_4_XML = new StringBuffer().append("<").append(RETURN_CODE).append(">").append(MSG_CODE_SUCESS).append("</").append(RETURN_CODE).append(">");
	
	// 可以考虑定义个消息表,配置消息,加载到redis缓存中
    // 消息MSG
    public static Map<String, String> msgMap  = new HashMap<String, String>();

    
	static {
        msgMap = new HashMap<String, String>();
        msgMap.put(MSG_CODE_SUCESS, "返回数据成功");
        msgMap.put(MSG_CODE_FAIL, "返回数据失败");
        msgMap.put(MSG_CODE_NO_DATA, "数据返回null");
        msgMap.put(MSG_CODE_1010, "系统异常");
        msgMap.put(MSG_CODE_1020, "请求入参有误");
        msgMap.put(MSG_CODE_1030, "参数格式不正确");
        msgMap.put(MSG_CODE_1040, "返回消息消息解析异常");
        msgMap.put(MSG_CODE_1050, "Api调用处理,方法名空异常！");
        msgMap.put(MSG_CODE_1060, "参数中没传入业务类型编码: business_type");
        msgMap.put(MSG_CODE_1011, "不可重复挂号或号已挂完！");
        msgMap.put(MSG_CODE_1013, "配置表f_websvr_config未匹配！");
        msgMap.put(MSG_CODE_1014, "执行SQL返回NULL，请检查！");
        msgMap.put(MSG_CODE_1015, "配置表f_websvr_config参数invoke_type不为1.(1、过程 0、代码)");
        msgMap.put(MSG_CODE_1016, "调用业务过程发生了异常！");
        msgMap.put(MSG_CODE_1017, "redis服务没有正常启动！");
	}
	
	/**
	 * xml格式共通错误消息返回体
	 * @param retCode
	 * @return
	 */
	public static String getErrRes4Xml(String retCode) {
		 return errMsg4Xml.toString().replace(A_FOR_CODE, retCode).replace(A_FOR_MSG, msgMap.get(retCode));
	}
	
	/**
	 * xml格式共通错误消息返回体
	 * @param retCode
	 * @return
	 */
	public static String getErrRes4Xml(String retCode, String retMsg) {
		 return errMsg4Xml.toString().replace(A_FOR_CODE, retCode).replace(A_FOR_MSG, retMsg);
	}
	
    public static void main(String arg[]) {
    	
    	System.out.println(errMsg4Xml);
    	
    	System.out.println(getErrRes4Xml(MSG_CODE_1010));
    }
	
}

