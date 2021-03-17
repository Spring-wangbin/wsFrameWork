package com.founder.bdyx.webservice.soap.xml;


import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

//import org.apache.log4j.Logger;
import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.webservice.core.data.YzActOrder;
import com.founder.bdyx.webservice.core.data.ZyDetailCharge;
import com.founder.bdyx.webservice.core.service.IHttpLog;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrConfig;
import com.founder.bdyx.webservice.core.service.IWsService;
import com.founder.bdyx.webservice.soap.xml.util.XmlUtil4Soap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Service
public class XmlHandleBp {

	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(XmlHandleBp.class);

    @Autowired
    IWsService wsService;

    @Autowired
    IHttpLog httpLog;

    public String exec(String inParam){
        _log.info("传入参数：" + inParam);
        String invokeResult = "";
        XmlUtil4Soap xmlUtil = null;
        try {
            try {
                _log.info("初始化xml对象");
                xmlUtil = new XmlUtil4Soap();
                xmlUtil.InitInput_Common(inParam);
            } catch (Throwable e) {
                _log.error("初始化xml对象失败：" + e.getMessage());
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1020);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                return invokeResult;
            }

            String nameMethod = xmlUtil.Method_Code;
            _log.info("业务编码：{}", nameMethod);
            if (StringUtils.isEmpty(nameMethod)){
                _log.error("业务编码不允许为空");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1040);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                return invokeResult;
            }

            List<WebsvrConfig> list = wsService.getConfig(nameMethod);
            _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
            if(CollectionUtils.isEmpty(list)) {
                _log.error("没有匹配到记录,请配置表f_websvr_config。");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1013);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                return invokeResult;
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
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1015);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                return invokeResult;
            }

            String[] _abc = parmasList.split("\\|");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < _abc.length; i++) {
                String _a = _abc[i];
                String _b = xmlUtil.inputMap.get(_a);
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

            List<String> result = wsService.execProc4ListString(strSql);
            _log.info("执行SQL返回结果：" + JSON.toJSONString(result));
            if (result == null || CollectionUtils.isEmpty(result)){
            	_log.info("执行SQL返回NULL，请检查！");
            	xmlUtil.setRetMsg(WsConstant.MSG_CODE_1014);
            	invokeResult = xmlUtil.getOut();
            	_log.warn(invokeResult);
            	return invokeResult;
            } else {
            	xmlUtil.sSqlResult = result.get(0);
            	invokeResult = xmlUtil.getOut();
            }
			

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            _log.error("输出XML转换发生了异常！" + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1040, e.getMessage());
            _log.warn(invokeResult);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            _log.error("redis 连接异常： " + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1017, e.getMessage());
            _log.warn(invokeResult);
        } catch (Exception e) {
            e.printStackTrace();
            _log.error("系统发生了异常！" + e.getMessage());
            xmlUtil.setRetMsg(WsConstant.MSG_CODE_1010);
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1010, e.getMessage());
            _log.warn(invokeResult);
        }
        return invokeResult;
    }

    /**
     * 参数解析处理（LIS、手麻重症统一调用）
     * @param inParam
     * @param func_id
     * @return
     */
    public String exec_LIS(String inParam, String func_id){
        _log.info("传入参数：" + inParam);
        long startTime = System.currentTimeMillis();
        String invokeResult = "";
        XmlUtil4Soap xmlUtil = null;
        try {
            try {
                _log.info("初始化xml对象");
                xmlUtil = new XmlUtil4Soap();
                xmlUtil.InitInput_Common(inParam);
                xmlUtil.Method_Code = func_id;
                xmlUtil.getInput_LIS();
            } catch (Throwable e) {
                _log.error("初始化xml对象失败：" + e.getMessage());
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1020);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1020, invokeResult);
                return invokeResult;
            }

            //sqlserver中 text数据类型 前端不能传null 否则报错 varbinary 与 text 类型不兼容
            inParam = inParam == null ? "":inParam;

            String nameMethod = xmlUtil.Method_Code;
            _log.info("业务编码：{}", nameMethod);
            if (StringUtils.isEmpty(nameMethod)){
                _log.error("业务编码不允许为空");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1040);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1040, invokeResult);
                return invokeResult;
            }

            List<WebsvrConfig> list = wsService.getConfig(nameMethod);
            _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
            if(CollectionUtils.isEmpty(list)) {
                _log.error("没有匹配到记录,请配置表f_websvr_config。");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1013);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1013, invokeResult);
                return invokeResult;
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
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1015);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1015, invokeResult);
                return invokeResult;
            }

            String invokeParms = "";
            if(parmasList != null){
                StringBuilder sb = new StringBuilder();
                String[] _abc = parmasList.split("\\|");
                for (int i = 0; i < _abc.length; i++) {
                    String _a = _abc[i];
                    String _b = xmlUtil.inputMap.get(_a);
                    String value = StringUtils.hasText(_b) ? _b : "";
                    _log.debug("参数" + _a + ":" + value);
                    value = "'" + value.replaceAll("\\|", ",") + "'";
                    sb.append(value);
                    if (i < _abc.length - 1) {
                        sb.append(",");
                    }
                }
                invokeParms = sb.toString();
            }

            _log.info("参数值：" + invokeParms);
            String strSql = "EXEC " + invokeProc + " " + invokeParms;
            _log.info("拼装完整SQL执行语句：" + strSql);

            List<Map<String,Object>> result = wsService.execProce4ListMap(strSql);
            _log.info("执行SQL返回结果：" + JSON.toJSONString(result));
            if (result == null || CollectionUtils.isEmpty(result) || result.size() <=0 ){
                _log.info("执行SQL返回NULL，请检查！");
                xmlUtil.setRetErrorMsg(WsConstant.MSG_CODE_NO_DATA);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_NO_DATA, invokeResult);
                return invokeResult;
            } else {
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_SUCESS);
                xmlUtil.setSqlData(result);
                invokeResult = xmlUtil.getOut();
                //插入日志
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_SUCESS, invokeResult);
            }


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            _log.error("输出XML转换发生了异常！" + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1040, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1040, invokeResult);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            _log.error("redis 连接异常： " + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1017, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1017, invokeResult);
        } catch (Exception e) {
            e.printStackTrace();
            _log.error("系统发生了异常！" + e.getMessage());
            xmlUtil.setRetMsg(WsConstant.MSG_CODE_1010);
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1010, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1010, invokeResult);
        }
        return invokeResult;
    }

    
    public  List<Map<String,Object>> exec_NSD(String inParam, String func_id){
        _log.info("传入参数：" + inParam);
        long startTime = System.currentTimeMillis();
        String invokeResult = "";
        List<Map<String,Object>> rtResult=null;
        XmlUtil4Soap xmlUtil = null;
        try {
            try {
                _log.info("初始化xml对象");
                xmlUtil = new XmlUtil4Soap();
                xmlUtil.InitInput_Common(inParam);
                xmlUtil.Method_Code = func_id;
                xmlUtil.getInput_LIS();
            } catch (Throwable e) {
                _log.error("初始化xml对象失败：" + e.getMessage());
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1020);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1020, invokeResult);
                return null;
            }

            //sqlserver中 text数据类型 前端不能传null 否则报错 varbinary 与 text 类型不兼容
            inParam = inParam == null ? "":inParam;

            String nameMethod = xmlUtil.Method_Code;
            _log.info("业务编码：{}", nameMethod);
            if (StringUtils.isEmpty(nameMethod)){
                _log.error("业务编码不允许为空");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1040);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1040, invokeResult);
                return null;
            }

            List<WebsvrConfig> list = wsService.getConfig(nameMethod);
            _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
            if(CollectionUtils.isEmpty(list)) {
                _log.error("没有匹配到记录,请配置表f_websvr_config。");
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1013);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1013, invokeResult);
                return null;
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
                xmlUtil.setRetMsg(WsConstant.MSG_CODE_1015);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1015, invokeResult);
                return null;
            }

            String invokeParms = "";
            if(parmasList != null){
                StringBuilder sb = new StringBuilder();
                String[] _abc = parmasList.split("\\|");
                for (int i = 0; i < _abc.length; i++) {
                    String _a = _abc[i];
                    String _b = xmlUtil.inputMap.get(_a);
                    String value = StringUtils.hasText(_b) ? _b : "";
                    _log.debug("参数" + _a + ":" + value);
                    value = "'" + value.replaceAll("\\|", ",") + "'";
                    sb.append(value);
                    if (i < _abc.length - 1) {
                        sb.append(",");
                    }
                }
                invokeParms = sb.toString();
            }

            _log.info("参数值：" + invokeParms);
            String strSql = "EXEC " + invokeProc + " " + invokeParms;
            _log.info("拼装完整SQL执行语句：" + strSql);

            List<Map<String,Object>> result = wsService.execProce4ListMap(strSql);
            _log.info("执行SQL返回结果：" + JSON.toJSONString(result));
            if (result == null || CollectionUtils.isEmpty(result) || result.size() <=0 ){
                _log.info("执行SQL返回NULL，请检查！");
                xmlUtil.setRetErrorMsg(WsConstant.MSG_CODE_NO_DATA);
                invokeResult = xmlUtil.getOut();
                _log.warn(invokeResult);
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_NO_DATA, invokeResult);
                return null;
            } else {
            	rtResult=result;
            	for(Map<String,Object> m:result){
            		invokeResult=invokeResult+String.valueOf(m.get("czInfo"));
            	}
            	//插入日志
                httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_SUCESS, invokeResult);
            }


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            _log.error("输出XML转换发生了异常！" + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1040, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1040, invokeResult);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
            _log.error("redis 连接异常： " + e.getMessage());
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1017, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1017, invokeResult);
        } catch (Exception e) {
            e.printStackTrace();
            _log.error("系统发生了异常！" + e.getMessage());
            xmlUtil.setRetMsg(WsConstant.MSG_CODE_1010);
            invokeResult = WsConstant.getErrRes4Xml(WsConstant.MSG_CODE_1010, e.getMessage());
            _log.warn(invokeResult);
            httpLog.log_webservice(startTime, inParam, func_id, WsConstant.MSG_CODE_1010, invokeResult);
        }
        return rtResult;
    }
    
    public List<Map<String,Object>> getInputToList(String inputParams) throws Throwable {
        List<Map<String,Object>> al = new ArrayList<>();

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(new ByteArrayInputStream(inputParams.toString().getBytes("UTF-8")));

        Element root = document.getDocumentElement();
        if(root.hasChildNodes()){
            NodeList nodeList = root.getElementsByTagName("data");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if(node.hasChildNodes()){
                    NodeList childNodes = node.getChildNodes();
                    Map<String,Object> retMap = new HashMap<String,Object>();
                    for(int j=0;j<childNodes.getLength();j++){
                        Node childNode = childNodes.item(j);
                        if(childNode.getNodeType() == Node.ELEMENT_NODE){
                            retMap.put(childNode.getNodeName(),childNode.getTextContent());
                        }
                    }
                    al.add(retMap);
                }
            }
        }

        return al;
    }

}
