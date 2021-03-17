package com.founder.bdyx.webservice.core.mapper;

import java.util.List;
import java.util.Map;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.task.domain.QueryLogMode;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;

//@Component
//@Mapper
public interface WebsvrLogMapper extends MyMapper<WebsvrLog> {
	
    int insertAndGetId(WebsvrLog log);
    
    
    List<Map<String,Object>> selectLogByDate(QueryLogMode query);
    
    List<WebsvrLog> queryWbLogByDate(QueryLogMode query);
}
