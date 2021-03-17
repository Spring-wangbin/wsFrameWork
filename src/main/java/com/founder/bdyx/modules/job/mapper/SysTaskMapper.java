package com.founder.bdyx.modules.job.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.job.model.SysTask;

public interface SysTaskMapper extends MyMapper<SysTask> {
    int batchRemove(Long[] ids);
}