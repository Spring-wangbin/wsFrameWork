package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysMenuRole;

public interface SysMenuRoleMapper extends MyMapper<SysMenuRole> {
    void deleteByRoleId(Integer roleId);
}