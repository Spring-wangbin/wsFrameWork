package com.founder.bdyx.webservice.soap.json.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;

import net.sf.json.JSONObject;

/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2019年12月23日 下午4:43:21
 */
public class JsonUtil4Soap {

	private static Logger log = Logger.getLogger(JsonUtil4Soap.class);

	
	public JSONObject jsongObjReq;
	public JSONObject jsonObjRes;

	public Map<String, String> inputMap;
	
	public static String Method_Code = "methodCode";
	

	/*
	 * {
		    "code": "0",
		    "message": "请求成功",
		    "data": [
		        {
		            "fromCode": "112628",
		            "fromOrgName": "北京大学国际医院",
		            "toCode": "112628",
		            "toOrgName": "北京大学国际医院"
		        }
		    ]
		}
	 */
	
	public JSONObject getInputJson(String reqStr) {
		jsongObjReq = new JSONObject();
		try {
			jsongObjReq = JSONObject.fromObject(reqStr);
		
		}
		catch (Exception e) {
	    	e.printStackTrace();
	    }
		return jsongObjReq;
	}
	
	public int getJsonResult(List<Map<String,Object>> result) throws IOException {
		int resCount = 0;
		if(result!=null && result.size() > 0) {
			// json对象
			jsonObjRes = new JSONObject();
			jsonObjRes.put(WsConstant.CODE, WsConstant.MSG_CODE_SUCESS);
			jsonObjRes.put(WsConstant.MESSAGE, WsConstant.msgMap.get(WsConstant.MSG_CODE_SUCESS));
			log.debug("array.size: " + result.size());
			resCount = result.size();
			if (resCount>0) {
				jsonObjRes.put(WsConstant.DATA, JSON.toJSONString(result));
			}
		} else {
			getJsonStrRes();
		}
		
		return resCount;
	}
	
	public String getJsonStrRes() {
		if (jsonObjRes.isEmpty()) {
			jsonObjRes = new JSONObject();
			jsonObjRes.put(WsConstant.CODE, WsConstant.MSG_CODE_1010);
			jsonObjRes.put(WsConstant.MESSAGE, WsConstant.msgMap.get(WsConstant.MSG_CODE_1010));
		}
		return jsonObjRes.toString();
	}
	
	
	/**
	 * 
	 * @param ret_code
	 * @param ret_msg
	 * @return
	 */
	public boolean setRetMsg(String ret_code, String ret_msg)
	{
		if (jsonObjRes==null || jsonObjRes.isEmpty())
				jsonObjRes = new JSONObject();
		else
			jsonObjRes.clear();
		jsonObjRes.put(WsConstant.CODE, ret_code);
		jsonObjRes.put(WsConstant.MESSAGE, ret_msg);
		//jsonObjRes.
		return true;
	}
	
	/**
	 * 
	 * @param ret_code
	 * @param ret_msg
	 * @return
	 */
	public boolean setRetMsg(String ret_code)
	{
		if (jsonObjRes==null || jsonObjRes.isEmpty())
				jsonObjRes = new JSONObject();
		else
			jsonObjRes.clear();
		jsonObjRes.put(WsConstant.CODE, ret_code);
		jsonObjRes.put(WsConstant.MESSAGE, WsConstant.msgMap.get(ret_code));
		//jsonObjRes.
		return true;
	}
}
