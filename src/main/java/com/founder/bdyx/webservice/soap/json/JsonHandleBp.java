package com.founder.bdyx.webservice.soap.json;


import java.util.List;
import java.util.Map;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrConfig;
import com.founder.bdyx.webservice.core.service.IWsService;
import com.founder.bdyx.webservice.soap.json.util.JsonUtil4Soap;

import redis.clients.jedis.exceptions.JedisConnectionException;

/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2019年12月23日 下午6:02:21
 */
@Service
public class JsonHandleBp {

	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(JsonHandleBp.class);

    @Autowired
    IWsService wsService;

    public String exec(String inParam) {
        _log.info("传入参数：" + inParam);
        JsonUtil4Soap jsonUtil = null;
        
        try {
            try {
                _log.info("初始化入参Json对象");
                jsonUtil = new JsonUtil4Soap();
                jsonUtil.getInputJson(inParam);
            } catch (Throwable e) {
                _log.error("初始化入参Json对象失败：" + e.getMessage());
                jsonUtil.setRetMsg(WsConstant.MSG_CODE_1020);
                _log.warn(jsonUtil.getJsonStrRes());
                return jsonUtil.getJsonStrRes();
            }

            String nameMethod = jsonUtil.jsongObjReq.getString(JsonUtil4Soap.Method_Code);
            _log.info("业务编码：{}", nameMethod);
            if (StringUtils.isEmpty(nameMethod)){
                _log.error("业务编码不允许为空");
                jsonUtil.setRetMsg(WsConstant.MSG_CODE_1060);
                _log.warn(jsonUtil.getJsonStrRes());
                return jsonUtil.getJsonStrRes();
            }

            List<WebsvrConfig> list = wsService.getConfig(nameMethod);
            _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
            if(CollectionUtils.isEmpty(list)) {
                _log.error("没有匹配到记录,请配置表f_websvr_config。");
                jsonUtil.setRetMsg(WsConstant.MSG_CODE_1013);
                _log.warn(jsonUtil.getJsonStrRes());
                return jsonUtil.getJsonStrRes();
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
                _log.warn(jsonUtil.getJsonStrRes());
                return jsonUtil.getJsonStrRes();
            }

            String[] _abc = parmasList.split("\\|");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < _abc.length; i++) {
                String _a = _abc[i];
                String _b = jsonUtil.jsongObjReq.getString(_a);
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
            	_log.warn(jsonUtil.getJsonStrRes());
            	return jsonUtil.getJsonStrRes();
            }
            
            // 返回对象
            jsonUtil.getJsonResult(result);
			
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            _log.error("redis 连接异常： " + e.getMessage());
            jsonUtil.setRetMsg(WsConstant.MSG_CODE_1017);
            _log.warn(jsonUtil.getJsonStrRes());
            return jsonUtil.getJsonStrRes();
        } catch (Exception e) {
            e.printStackTrace();
            jsonUtil.setRetMsg(WsConstant.MSG_CODE_1010, e.getLocalizedMessage());
            _log.warn(jsonUtil.getJsonStrRes());
            return jsonUtil.getJsonStrRes();
        }
        
        return jsonUtil.getJsonStrRes();
    }
}
