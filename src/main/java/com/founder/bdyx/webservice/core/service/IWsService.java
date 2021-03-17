package com.founder.bdyx.webservice.core.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.founder.bdyx.webservice.core.domain.WebsvrConfig;

public interface IWsService {

    List<WebsvrConfig> getConfig(String funcId);

	/**
	 * 根据存储过程查询,返回List< String >
	 * @param procSql
	 * @return List< String >
	 */
    List<String> execProc4ListString(@Param("procSql") String procSql);
    
	/**
	 * 根据存储过程查询,返回List< Map<String,Object> >
	 * @param procSql
	 * @return List< Map<String,Object> >
	 */
    List<Map<String,Object>> execProce4ListMap(@Param("procSql") String procSql);
    
    
	/**
	 * 根据存储过程查询,返回List< T >
	 * @param <T>
	 * @param procSql
	 * @return List< Map<String,Object> >
	 */
    <T> List<T> execProce4ListObject(@Param("procSql") String procSql, Class<T> c);
    
}
