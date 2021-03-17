
package com.founder.bdyx.modules.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.founder.bdyx.core.result.JsonResult;
import com.founder.bdyx.core.result.PageResult;
import com.founder.bdyx.core.util.FileUtil;
import com.founder.bdyx.modules.sys.model.SysUser;
import com.founder.bdyx.modules.sys.service.WsLogService;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * @author yang.xuefeng
 */
@Api(value ="请求服务日志", description = "请求服务日志Api",tags = {"请求服务日志接口"})
@RestController
@RequestMapping("/wssys/log")
public class WsLogController {

    @Autowired
    private WsLogService wsLogService;

    @ApiOperation(value = "服务日志列表视图",notes = "服务日志列表视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/sys/wsLog/list");
    }

    @ApiOperation(value = "获取日志列表",notes = "获取日志列表")
    @GetMapping
    public PageResult<WebsvrLog> getAll(@ApiParam(name = "WebsvrLog",value="服务日志实体",required = true) WebsvrLog websvrLog,
                                         @ApiParam(name = "logDate",value="服务日志时间",required = false) @Param("logDate") String logDate,
                                         @ApiParam(name = "keyword",value="查询字段:如用户名，操作",required = false) String keyword,
                                         HttpServletRequest request) {
        SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
        if(sysUser==null){
            new PageResult();
        }
        List<WebsvrLog> logList = wsLogService.getListByPage(websvrLog,keyword, logDate);
        return new PageResult(new PageInfo<WebsvrLog>(logList));
    }

    @ApiOperation(value = "导出日志",notes = "导出日志")
    @GetMapping(value="/export")
    public  JsonResult<Integer>  downLoginLogExcel(HttpServletResponse response,
                                                   @ApiParam(name = "logDate",value="服务日志时间",required = false) @Param("logDate") String logDate,
                                                   @ApiParam(name = "keyword",value="查询字段:如用户名，操作",required = false) String keyword,
                                                   HttpServletRequest request){
        try{
            SysUser sysUser=(SysUser)request.getSession().getAttribute("user");
            if(sysUser==null){
                new JsonResult<Integer>(1,"用户未登录！");
            }
            List<WebsvrLog> logList = wsLogService.getList(null,keyword,logDate);
            FileUtil.downloadFile(response, wsLogService.getExcel(logList));
            return new JsonResult<>(0);
        }catch (Exception e){
            return new JsonResult<>(e);
        }
    }


}
