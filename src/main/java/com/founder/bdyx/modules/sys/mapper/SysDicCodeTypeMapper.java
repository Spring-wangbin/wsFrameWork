package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysDicCodeType;

public interface SysDicCodeTypeMapper extends MyMapper<SysDicCodeType> {
    int myInsertUseGeneratedKeys(SysDicCodeType bscDicCodeType);
}