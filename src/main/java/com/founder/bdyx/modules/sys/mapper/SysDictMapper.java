package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.entity.ProcessResult;
import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysDict;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysDictMapper extends MyMapper<SysDict> {
    SysDict getDictValueChild(@Param("param") Map<String, Object> param);
}