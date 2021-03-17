package com.founder.bdyx.task;

import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.task.domain.CisObs;
import com.founder.bdyx.webservice.core.constants.WsConstant;
import com.founder.bdyx.webservice.core.domain.WebsvrConfig;
import com.founder.bdyx.webservice.core.mapper.CisObsMapper;
import com.founder.bdyx.webservice.core.service.IWsService;

/**
* @Description 功能描述
* @author yang.xuefeng
* @version 创建时间：2020年1月3日 下午2:49:07
* TODO:定时任务示例参考代码
*    a.存储过程返回 List<map>对象
*    b.存储过程返回List<Object>
*    c.java调用第三方 WS接口
*    d.mybati直接更新实体类对象
*/
@Component
public class SyncPacsChargeTask implements Job {
	
	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(SyncPacsChargeTask.class);
	@Autowired
	CisObsMapper  cisObsMapper;
	
    @Autowired
    IWsService wsService;
    
	@Value("${pacs_ws}")
	private String PACS_WS_URL;
	
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	
    	// 1. 查 cis_obs表需要同步费用状态的数据  charge_stauts=1, sync_flag=0  ==>通过过程查询
    	
        List<WebsvrConfig> list = wsService.getConfig("PROC_QUERY_CIS_OBS");
        _log.info("根据业务编码查询配置表：{}", JSON.toJSONString(list));
        if(CollectionUtils.isEmpty(list)) {
            _log.error("没有匹配到记录,请配置表f_websvr_config。");
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
            return ;
        }

        String[] _abc = parmasList.split("\\|");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < _abc.length; i++) {
            String _a = _abc[i];
            String _b = "1";
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
        String procSql = "EXEC " + invokeProc + " " + invokeParms;
        _log.info("拼装完整SQL执行语句：" + procSql);

        List<CisObs> result = wsService.execProce4ListObject(procSql, CisObs.class);
        _log.info("执行SQL返回结果：" + JSON.toJSONString(result));
        if (result == null || CollectionUtils.isEmpty(result)){
        	_log.info("执行SQL返回NULL，请检查！");
        	return ;
        }
        
        System.out.println(JSON.toJSONString(result));
    	
    	// 2. 调Pacs 提供的费用状态同步接口
//        String applyNos = "";
//        for(CisObs obs: result){
//        	applyNos = applyNos + obs.getApply_no() + ",";
//        }
//        applyNos = applyNos.substring(0,applyNos.length() -1);
//        callPacsWs(applyNos);
//        
        for(CisObs obs: result){
        	int rtn = callPacsWs(String.valueOf(obs.getApply_no()));
        	if (rtn == 0) {
            	// 3. 如果pacs接口调用成功（同步成功), 回更新 cis_obs的同步状态字段(sync_flag=1)
        		obs.setSync_flag(1); // 设置同步成功的Flag
        		cisObsMapper.updateByPrimaryKeySelective(obs);
        	} else {
        		// .................
        	}
        }
    	
    	// 3. 如果pacs接口调用成功（同步成功), 回更新 cis_obs的同步状态字段(sync_flag=1)
        
        
    }
    
    private int callPacsWs(String applyNos) {
    	
    	int ret =1; // 0: 成功 1:失败
    	StringBuffer sb = new StringBuffer();
    	sb.append("<xml>").
    	append("<Header>").
    	append("<Application>HIS</Application>").
    	append("<methodCode>PACS_SYNC_CHARGE_FLAG</methodCode>").
    	append("</Header>").
    	append("<methodParam>").
    	append("<applyNo>AAAAA_NO</applyNo>").
    	append("<chargeFlag>1</chargeFlag>").
    	append("</methodParam>").
    	append("</xml>");
    	String requestXML = sb.toString().replace("AAAAA_NO", applyNos);
    	
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(PACS_WS_URL);
		Object[] objects = new Object[0];
		try {
			
			objects = client.invoke("SyncChargeFlag", new Object[]{requestXML});
			
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		
		// 判断是否成功
		String retXML = objects[0].toString();
		if (retXML.contains(WsConstant.RTN_SUCC_4_XML.toString())) {
			ret = 0;
		}
		
		return ret;
    }
}
