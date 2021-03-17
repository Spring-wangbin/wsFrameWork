package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysDept;
import com.founder.bdyx.modules.sys.model.SysUser;

import java.util.List;

public interface SysDeptMapper extends MyMapper<SysDept> {
    List<SysDept> ListTopDept();

    List<SysDept> getChildDeptList(Integer id);
}