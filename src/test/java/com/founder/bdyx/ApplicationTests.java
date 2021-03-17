package com.founder.bdyx;

import java.util.List;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.founder.bdyx.model.queryHttpJson;
import com.founder.bdyx.model.queryPatDayCharge;
import com.founder.bdyx.modules.sys.mapper.SysMenuMapper;
import com.founder.bdyx.modules.sys.mapper.SysUserMapper;
import com.founder.bdyx.modules.sys.model.SysMenu;
import com.founder.bdyx.modules.sys.model.SysUser;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysUserMapper sysUserMapper;

	@Test
	public void test1() {
		List<SysMenu> dd = sysMenuMapper.selectAll();
		System.out.println(dd);
	}

	@Test
	public void test2() {
		List<SysUser> dd = sysUserMapper.selectAll();
		System.out.println(dd);
	}

	@Test
	public void CallSoap() {
		// 创建动态客户端
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:9009/services/json?wsdl");
		Object[] objects = new Object[0];
		try {
//			objects = client.invoke("doBusiness", "json参数结构体");
			
			queryPatDayCharge query = new queryPatDayCharge();
			query.setMethodCode("APC_QRY_PAT_GETDAYLIST");
			query.setPatientId("000202312300");
			query.setTimes(1);
			query.setStartDate("2016-01-22");
			query.setEndDate("2017-01-22");
			
			objects = client.invoke("doBusiness", JSON.toJSONString(query));
			
			System.out.println("返回数据:" + objects[0]);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

	
//	@Test
//	public void CallHttpJson() {
//		RestTemplate restTemplate = new RestTemplate();
//	    String url = "http://localhost:9009/deptsjson";
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//	    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//	    map.add("hos_code", "H109472");
//
//	    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//	    ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
//	    
//	    System.out.println(response.getBody());
//	}
	
	
	@Test
	public void CallHttpJson2() {
		RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:9009/deptsjson";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("hos_code","H109472");

        HttpEntity<String> entity = new HttpEntity<>(jsonObj.toString(), headers);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, 
                                          HttpMethod.POST, entity, JSONObject.class);
        System.out.println(exchange.getBody());
	}
	
	@Test
	public void CallHttpJson3() {
		RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:9009/deptsjson";
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("hos_code","H109472");

        HttpEntity<String> entity = new HttpEntity<>(jsonObj.toString(), headers);
        ResponseEntity<JSONObject> response = restTemplate.postForEntity(url, entity, JSONObject.class);
        System.out.println(response.getBody());
	}
	
}
