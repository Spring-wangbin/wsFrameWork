package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends MyMapper<SysUser> {
    List<SysUser> getIsNotOwner(@Param("roleId") Integer roleId,@Param("keyword")String keyword);

    List<SysUser> getIsOwner(@Param("roleId") Integer roleId,@Param("keyword")String keyword);

    SysUser getMasterUserByDept(Integer deptId);

    List<SysUser> isUnAllotUserList(Integer deptId);

    List<SysUser> isAllotUserList(Integer deptId);
}