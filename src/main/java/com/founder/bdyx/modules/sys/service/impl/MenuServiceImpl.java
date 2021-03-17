
package com.founder.bdyx.modules.sys.service.impl;


import com.founder.bdyx.modules.sys.mapper.SysMenuMapper;
import com.founder.bdyx.modules.sys.mapper.SysMenuRoleMapper;
import com.founder.bdyx.modules.sys.model.SysMenu;
import com.founder.bdyx.modules.sys.model.SysMenuRole;
import com.founder.bdyx.modules.sys.model.SysRole;
import com.founder.bdyx.modules.sys.model.SysUser;
import com.founder.bdyx.modules.sys.service.MenuService;
import com.founder.bdyx.modules.sys.vo.SysMenuVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yang.xuefeng
 */
@Service("menuService")
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysMenuRoleMapper sysMenuRoleMapper;


    @Override
    public List<SysMenuVo> getTreeList() {
        List<SysMenu> topList=sysMenuMapper.getTopList();
        return convertList(topList);
    }

    @Override
    public List<SysMenu> getAll(SysMenu sysMenu) {
        return sysMenuMapper.select(sysMenu);
    }

    @Override
    public SysMenu getById(Integer id) {
        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SysMenu sysMenu) {
        if(sysMenu.getId()!=null){//修改
            sysMenuMapper.updateByPrimaryKey(sysMenu);
        }else{
            sysMenuMapper.insert(sysMenu);
        }
    }

    @Override
    public void deleteById(Integer id) {
        sysMenuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<SysMenu> getSelMenuPermission(Integer roleId) {
        return sysMenuMapper.getSelMenuPermission(roleId);
    }

    @Override
    public void saveMenuPermission(Integer roleId, Integer[] ids) {
        //先删除改角色的权限，再保存权限
        sysMenuRoleMapper.deleteByRoleId(roleId);
        if(ids.length>0){
           for (int i=0;i<ids.length;i++){
               Integer id=ids[i];
               if(id!=null){
                   SysMenuRole sysMenuRole=new SysMenuRole();
                   sysMenuRole.setRoleId(roleId);
                   sysMenuRole.setMenuId(ids[i]);
                   sysMenuRoleMapper.insert(sysMenuRole);
               }
           }
        }
    }

    @Override
    public List<SysMenuVo> treeListPermission(SysUser sysUser) {
        SysRole sysRole = sysUser.getRoles().iterator().next();
        Integer roleId = sysRole.getId();
        if(roleId!=null){
            List<SysMenu> permission = getSelMenuPermission(roleId);
            List<SysMenu> topList=sysMenuMapper.getTopList();
            topList=hasPermission(topList,permission);
            return convertListPermission(topList,permission);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SysMenuVo> treeListPermissionByRoleId(Integer roleId) {
        if(roleId!=null){
            List<SysMenu> permission = getSelMenuPermission(roleId);
            List<SysMenu> topList=sysMenuMapper.getTopList();
            topList=hasPermission(topList,permission);
            return convertListPermission(topList,permission);
        }
        return new ArrayList<>();
    }

    @Override
    public Set<SysMenuVo> treeSetPermissionByRoleId(Integer roleId) {
        List<SysMenuVo> list=new ArrayList<>();
        if(roleId!=null){
            List<SysMenu> permission = getSelMenuPermission(roleId);
            List<SysMenu> topList=sysMenuMapper.getTopList();
            topList=hasPermission(topList,permission);
            list=convertListPermission(topList,permission);
        }
        return new HashSet<SysMenuVo>(list);
    }



    private List<SysMenuVo> convertListPermission(List<SysMenu> toplist,List<SysMenu> permission) {
        List<SysMenuVo> result=new ArrayList<>();
        if(toplist.size()>0){
            for (SysMenu sysMenu:toplist){
                SysMenuVo vo=new SysMenuVo();
                BeanUtils.copyProperties(sysMenu,vo);
                List<SysMenu> list=sysMenuMapper.getChildDeptList(sysMenu.getId());
                list=hasPermission(list,permission);
                if(list.size()>0){
                    vo.setChildren(convertListPermission(list,permission));
                }
                result.add(vo);
            }
        }
        return result;
    }

    private List<SysMenu> hasPermission(List<SysMenu> menus,List<SysMenu> permission){
        List<SysMenu> results=new ArrayList<>();
        if(menus.size()>0 && permission.size()>0){
            for (SysMenu menu:menus){
                if(hasPermission(permission,menu)){
                    results.add(menu);
                }
            }
        }
        return results;
    }


    private boolean hasPermission(List<SysMenu> permission,SysMenu sysMenu){
        if(permission.size()>0){
            for (SysMenu menu:permission){
                if(sysMenu.getId().equals(menu.getId())){
                    return true;
                }
            }
        }
        return false;
    }


    public List<SysMenuVo> convertList(List<SysMenu> toplist){
        List<SysMenuVo> result=new ArrayList<>();
        if(toplist.size()>0){
            for (SysMenu sysMenu:toplist){
                SysMenuVo vo=new SysMenuVo();
                BeanUtils.copyProperties(sysMenu,vo);
                List<SysMenu> list=sysMenuMapper.getChildDeptList(sysMenu.getId());
                if(list.size()>0){
                    vo.setChildren(convertList(list));
                }
                result.add(vo);
            }
        }
        return result;
    }
}
