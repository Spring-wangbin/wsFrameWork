package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysMenu;

import java.util.List;

public interface SysMenuMapper extends MyMapper<SysMenu> {
    List<SysMenu> getTopList();

    List<SysMenu> getChildDeptList(Integer id);

    List<SysMenu> getSelMenuPermission(Integer roleId);
}