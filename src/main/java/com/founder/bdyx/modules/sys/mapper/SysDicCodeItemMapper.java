package com.founder.bdyx.modules.sys.mapper;

import com.founder.bdyx.core.mapper.MyMapper;
import com.founder.bdyx.modules.sys.model.SysDicCodeItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDicCodeItemMapper extends MyMapper<SysDicCodeItem> {
    List<SysDicCodeItem> getBscDicCodeItemListByTypeCode(String typeCode);

    SysDicCodeItem getBscDicCodeItemListByTypeCodeAndItemCode(@Param("typeCode") String typeCode,@Param("itemCode") String itemCode);

    void deleteByTypeId(Integer id);
}