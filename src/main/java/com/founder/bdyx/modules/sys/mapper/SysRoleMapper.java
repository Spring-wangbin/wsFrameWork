package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysRole;

import java.util.List;

public interface SysRoleMapper extends MyMapper<SysRole> {
    List<SysRole> findByUserId(Integer userId);
}