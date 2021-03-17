package com.founder.bdyx.webservice.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.founder.bdyx.webservice.core.domain.WebsvrConfig;
import com.founder.bdyx.webservice.core.mapper.WebsvrConfigMapper;
import com.founder.bdyx.webservice.core.service.IWsService;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author yang.xuefeng
 *
 */
@Service
@Transactional
//@CacheConfig(cacheNames = {"f_websvr_config"})
public class WsServiceImpl implements IWsService {

    @Autowired
    WebsvrConfigMapper websvrConfigMapper;

    //@Cacheable(key = "#funcId")
    public List<WebsvrConfig> getConfig(String funcId) {
        Example example=new Example(WebsvrConfig.class);
        Example.Criteria criteria = example.createCriteria();
        
        if(StringUtils.isNotBlank(funcId)){
            criteria.andEqualTo("func_id",funcId);
        }

        List<WebsvrConfig> list = websvrConfigMapper.selectByExample(example);
        return list;
    }

    // 过期时间为5秒
    //@Cacheable(value = "5m", key = "#procSql")
    public List<String> execProc4ListString(String procSql) {
        return websvrConfigMapper.queryListStrByProc(procSql);
    }
    
    //@Cacheable(value = "5m", key = "#procSql")
    public List<Map<String,Object>> execProce4ListMap(String procSql) {
        return websvrConfigMapper.queryListMapByProc(procSql);
    }
    
	/**
	 * 根据存储过程查询,返回List< Map<String,Object> >
	 * @param <T>
	 * @param procSql
	 * @return List< Map<String,Object> >
	 */
    //@Cacheable(value = "5m", key = "#procSql")
    public <T> List<T> execProce4ListObject(@Param("procSql") String procSql, Class<T> clazz) {
    	List<Map<String,Object>>  listMap = websvrConfigMapper.queryListMapByProc(procSql);
    	if (listMap == null || listMap.size() == 0) {
    		return new ArrayList<T>();
    	}
    	List<T> rtnList = new ArrayList<T>();
    	for ( Map<String, Object> map : listMap) {
    		  
    		T obj;
			try {
				obj = clazz.newInstance();
				BeanUtils.populate(obj, map); // map转对象
				rtnList.add(obj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return rtnList;
    }
    
}
