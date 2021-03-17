
package com.founder.bdyx.modules.sys.controller;

import com.founder.bdyx.core.annotation.SysLog;
import com.founder.bdyx.core.entity.ProcessResult;
import com.founder.bdyx.core.result.PageResult;
import com.founder.bdyx.modules.sys.model.SysUser;
import com.founder.bdyx.modules.sys.service.UserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.founder.bdyx.core.entity.ProcessResult.ERROR;

/**
 * @author yang.xuefeng
 * 
 */
@Api(value ="用户管理模块", description = "用户Api",tags = {"用户管理操作接口"})
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户列表视图",notes = "用户列表视图")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/sysUser/list");
    }

    @ApiOperation(value = "用户表单视图",notes = "用户表单视图")
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public ModelAndView form() {
        return new ModelAndView("/modules/sys/sysUser/form");
    }

    @ApiOperation(value = "获取用户列表",notes = "获取用户列表")
    @RequestMapping(value = "",method = RequestMethod.GET)
    public PageResult<SysUser> getAll(@ApiParam(name = "sysUser",value="用户实体",required = true) SysUser sysUser,
                                      @ApiParam(name = "keyword",value="查询字段",required = false) String keyword) {
        List<SysUser> userInfoList = userService.getAll(sysUser,keyword);
        return new PageResult(new PageInfo<SysUser>(userInfoList));
    }

    @ApiOperation(value = "批量删除用户",notes = "批量删除用户")
    @SysLog("批量删除用户")
    @PostMapping(value = "/batchDelete")
    public ProcessResult batchDelete(@ApiParam(name = "ids",value="用户ID数组",required = true) @RequestParam("ids[]") Integer[] ids) {
        try {
            userService.batchDelete(ids);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "保存用户信息",notes = "保存用户信息")
    @SysLog("保存用户信息")
    @PostMapping(value = "/saveOrUpdate")
    public ProcessResult saveOrUpdate(@ApiParam(name = "sysUser",value="用户实体",required = true) SysUser sysUser) {
        try {
            userService.saveOrUpdate(sysUser);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }


    @ApiOperation(value = "获取用户信息",notes = "获取用户信息")
    @PostMapping(value = "/view/{id}")
    public SysUser view(@ApiParam(name = "id",value="用户ID",required = true) @PathVariable Integer id) {
        SysUser userInfo = userService.getById(id);
        return userInfo;
    }

    @ApiOperation(value = "删除用户",notes = "删除用户")
    @SysLog("删除用户")
    @PostMapping(value = "/delete/{id}")
    public ProcessResult delete(@ApiParam(name = "id",value="用户ID",required = true) @PathVariable Integer id) {
        try {
            userService.deleteById(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取已分配角色的用户",notes = "获取已分配角色的用户")
    @GetMapping(value = "/allotUser/getIsOwner/{roleId}/{keyword}")
    public Map<String,Object> getIsOwner(@ApiParam(name = "roleId",value="角色ID",required = true) @PathVariable Integer roleId,
                                         @ApiParam(name = "keyword",value="查询字段,初始为null",required = true) @PathVariable String keyword){
        Map<String,Object> map=new HashMap<>();
        try{
            if("null".equals(keyword)){
                keyword="";
            }
            List<SysUser> list=userService.getIsOwner(roleId,keyword);
            map.put("total",list.size());
            map.put("rows",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @ApiOperation(value = "获取未分配角色的用户",notes = "获取未分配角色的用户")
    @GetMapping(value = "/allotUser/getIsNotOwner/{roleId}/{keyword}")
    public Map<String,Object> getIsNotOwner(@ApiParam(name = "roleId",value="角色ID",required = true) @PathVariable Integer roleId,
                                            @ApiParam(name = "keyword",value="查询字段,初始为null",required = true) @PathVariable String keyword){
        Map<String,Object> map=new HashMap<>();
        try{
            if("null".equals(keyword)){
                keyword="";
            }
            List<SysUser> list=userService.getIsNotOwner(roleId,keyword);
            map.put("total",list.size());
            map.put("rows",list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @ApiOperation(value = "分配角色",notes = "分配角色")
    @PostMapping(value = "/allotUserById")
    public ProcessResult allotUserById(@ApiParam(name = "userId",value="用户ID",required = true) Integer userId,
                                       @ApiParam(name = "roleId",value="角色ID",required = true) Integer roleId,
                                       @ApiParam(name = "type",value="操作类型:0 移除，1 添加",required = true) Integer type) {
        try {
            userService.allotUserById(userId,roleId,type);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

}
