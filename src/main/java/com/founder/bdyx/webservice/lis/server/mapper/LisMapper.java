package com.founder.bdyx.webservice.lis.server.mapper;

import com.founder.bdyx.task.domain.QueryLogMode;

import java.util.List;
import java.util.Map;

public interface LisMapper {
    List<Map<String,Object>> requestInp(String param);
}
