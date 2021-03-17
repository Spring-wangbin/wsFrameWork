package com.founder.bdyx.webservice.http.json;

import com.founder.bdyx.webservice.core.service.impl.WsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "testJSON",tags = {"测试信123息"},produces = "application/json")
@Controller
public class TestController {

    @Resource
    private WsServiceImpl wsService;

    @ApiOperation(value = "test接口")
    @RequestMapping("/testController")
    @ResponseBody
    public Map test2(){

        List<String> al = wsService.execProc4ListString("aaaa");

        System.out.println(al);
        Map map = new HashMap();
        map.put("aaa","a");
        map.put("bbb","b");
        return map;
    }
}
