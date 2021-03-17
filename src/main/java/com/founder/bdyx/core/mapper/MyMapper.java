
package com.founder.bdyx.core.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 继承自己的MyMapper
* @author yang.xuefeng
* @version 创建时间：2020年12月26日 下午2:49:07
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    //FIXME 特别注意，该接口不能被扫描到，否则会出错

	/**
	 * 根据存储过程查询,返回List< String >
	 * @param procSql
	 * @return List< String >
	 */
    List<String> queryListStrByProc(@Param("procSql") String procSql);

	/**
	 * 根据存储过程查询,返回List< Map<String,Object> >
	 * @param procSql
	 * @return List< Map<String,Object> >
	 */
    List<Map<String, Object>> queryListMapByProc(@Param("procSql") String procSql);
}
