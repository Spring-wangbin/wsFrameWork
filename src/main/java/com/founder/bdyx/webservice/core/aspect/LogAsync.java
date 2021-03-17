package com.founder.bdyx.webservice.core.aspect;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.founder.bdyx.webservice.core.mapper.WebsvrLogMapper;

@Service
public class LogAsync {

	private static final Logger _log = org.slf4j.LoggerFactory.getLogger(LogAsync.class);

    @Autowired
    WebsvrLogMapper websvrLogMapper;

    @Async
    public Future<WebsvrLog> saveLog(WebsvrLog websvrLog) {
        _log.info("执行异步任务插入jyt日志记录");
        websvrLogMapper.insertAndGetId(websvrLog);
        _log.info("插入后回写：" + JSON.toJSONString(websvrLog));
        return new AsyncResult<>(websvrLog);
    }

    @Async
    public Future<WebsvrLog> updateLog(WebsvrLog websvrLog) {
        _log.info("执行异步任务更新jyt日志记录");
        websvrLogMapper.updateByPrimaryKeySelective(websvrLog);
        _log.info("插入后回写：" + JSON.toJSONString(websvrLog));
        return new AsyncResult<>(websvrLog);
    }
}
