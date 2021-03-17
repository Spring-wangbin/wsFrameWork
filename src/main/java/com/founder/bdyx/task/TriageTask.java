package com.founder.bdyx.task;

import java.util.List;
import java.util.Map;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.founder.bdyx.webservice.core.mapper.WebsvrConfigMapper;

@Component
public class TriageTask implements Job {
	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(TriageTask.class);
	@Value("${triageTask_url}")
	private String NSD_WS_URL;
	@Autowired
    WebsvrConfigMapper websvrConfigMapper;
	@Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
		_log.info("WELCOME TriageTask");
		//1 获取到需要传输给南师大分诊接口的数据
		String procSql="exec mzgh_getToNanshiDaInfo '1'";
		List<Map<String,Object>> ls = websvrConfigMapper.queryListMapByProc(procSql);
		if(ls.size()>0){
			//2组装xml数据
			String xmlInfo = "<?xml version=\"1.0\" encoding=\"GB2312\"?><Request>";
			String leds="|";
			for(int i=0;i<ls.size();i++){
				xmlInfo=xmlInfo+String.valueOf(ls.get(i).get("mzghInfo"));
				leds=leds+String.valueOf(ls.get(i).get("ledFlagInf"))+"|";
			}
			leds=leds.substring(0, leds.length()-1);
			xmlInfo=xmlInfo+"</Request>";
			_log.info("调用NanShiDa分诊接口xml数据："+xmlInfo);
			//3调用南师大分诊接口
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(NSD_WS_URL);	
			Object[] rs =null;
			try {
				rs = client.invoke("ReceiveClinicRegist", new Object[] { xmlInfo,0 });
				String rs_s = String.valueOf(rs[0]);
				_log.info("NanShiDa返回：" + rs_s);
				String retCode = rs_s.indexOf("<ResultCode>")>-1?rs_s.substring(rs_s.indexOf("<ResultCode>")+12, rs_s.indexOf("</ResultCode>")):"";
				String retMsg = rs_s.indexOf("<ResultContent>")>-1?rs_s.substring(rs_s.indexOf("<ResultContent>")+12, rs_s.indexOf("</ResultContent>")):"";
				if(null==retCode||"".equals(retCode)||"null".equals(retCode)){
					//说明调用失败
					 _log.info("此次调用南师大分诊接口失败！");
				    String procUpdate="exec mzgh_updateToNanshiDaInfoErr '1','"+leds+"',"+ls.size()+",'"+retMsg+"'";
					_log.info(procUpdate);
					List<String> ls1= websvrConfigMapper.queryListStrByProc(procUpdate);
					String retCode_local = ls1.get(0);
					if("0".equals(retCode_local)){
						_log.info("此次调用南师大分诊接口失败后更新本地数据成功");
					}else{
						_log.info("此次调用南师大分诊接口失败后更新本地数据失败，请查看interface_err_log表");
					}
				}else if("0".equals(retCode)){
					//调用成功
					//4获取返回数据并更新本地数据
					String procUpdate="exec mzgh_updateToNanshiDaInfo '1','"+leds+"',"+ls.size();
					_log.info(procUpdate);
					List<String> ls1= websvrConfigMapper.queryListStrByProc(procUpdate);
					String retCode_local = ls1.get(0);
					if("0".equals(retCode_local)){
						_log.info("此次调用南师大分诊接口后更新本地数据成功");
					}else{
						_log.info("此次调用南师大分诊接口后更新本地数据失败，请查看interface_err_log表");
					}
				}else{
					//调用失败
					 _log.info("此次调用南师大分诊接口失败！"+retMsg);
					 String procUpdate="exec mzgh_updateToNanshiDaInfoErr '1','"+leds+"',"+ls.size()+",'"+retMsg+"'";
					_log.info(procUpdate);
					List<String> ls1= websvrConfigMapper.queryListStrByProc(procUpdate);
					String retCode_local = ls1.get(0);
					if("0".equals(retCode_local)){
						_log.info("此次调用南师大分诊接口失败后更新本地数据成功");
					}else{
						_log.info("此次调用南师大分诊接口失败后更新本地数据失败，请查看interface_err_log表");
					}
				}
			} catch (java.lang.Exception e) {
				String retMsg = e.getMessage();
			     _log.info("此次调用南师大分诊接口失败！"+retMsg);
				 String procUpdate="exec mzgh_updateToNanshiDaInfoErr '1','"+leds+"',"+ls.size()+",'"+retMsg+"'";
				_log.info(procUpdate);
				List<String> ls1= websvrConfigMapper.queryListStrByProc(procUpdate);
				String retCode_local = ls1.get(0);
				if("0".equals(retCode_local)){
					_log.info("此次调用南师大分诊接口失败后更新本地数据成功");
				}else{
					_log.info("此次调用南师大分诊接口失败后更新本地数据失败，请查看interface_err_log表");
				}
			}
		}else{
			_log.info("无待分诊人员需要处理！");
		}
    }
}
