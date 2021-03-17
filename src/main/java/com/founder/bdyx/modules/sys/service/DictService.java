
package com.founder.bdyx.modules.sys.service;

import com.founder.bdyx.modules.sys.mapper.SysDicCodeItemMapper;
import com.founder.bdyx.modules.sys.mapper.SysDicCodeTypeMapper;
import com.founder.bdyx.modules.sys.model.SysDicCodeItem;
import com.founder.bdyx.modules.sys.model.SysDicCodeType;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author yang.xuefeng
 */
@Service
@Transactional
public class DictService{
    @Autowired
    private SysDicCodeItemMapper bscDicCodeItemMapper;

    @Resource
    private SysDicCodeTypeMapper bscDicCodeTypeMapper;

    public void saveOrUpdateItem(SysDicCodeItem bscDicCodeItem) {

        if (bscDicCodeItem.getId() == null) {
            if(StringUtils.isBlank(bscDicCodeItem.getIsActive())){
                bscDicCodeItem.setIsActive("0");
            }
            bscDicCodeItemMapper.insert(bscDicCodeItem);
        }else{
            bscDicCodeItemMapper.updateByPrimaryKey(bscDicCodeItem);
        }
    }

    public void deleteItem(Integer id) {
        if (id != null ) {
            bscDicCodeItemMapper.deleteByPrimaryKey(id);
        }
    }

    public SysDicCodeItem view(Integer id) {
        return bscDicCodeItemMapper.selectByPrimaryKey(id);
    }




    public void saveOrUpdateType(SysDicCodeType bscDicCodeType) {

        if (bscDicCodeType.getId() == null) {
            if(StringUtils.isBlank(bscDicCodeType.getIsActive())){
                bscDicCodeType.setIsActive("0");
            }
           bscDicCodeTypeMapper.myInsertUseGeneratedKeys(bscDicCodeType);
        }else{
            bscDicCodeTypeMapper.updateByPrimaryKeySelective(bscDicCodeType);
        }
    }

    public void deleteType(Integer id) {
        if (id != null ) {
            bscDicCodeTypeMapper.deleteByPrimaryKey(id);
            bscDicCodeItemMapper.deleteByTypeId(id);
        }
    }

    public SysDicCodeType viewType(Integer id) {
        return bscDicCodeTypeMapper.selectByPrimaryKey(id);
    }

    public List<SysDicCodeItem> getBscDicCodeItemListByTypeCode(String typeCode) {
        return bscDicCodeItemMapper.getBscDicCodeItemListByTypeCode(typeCode);
    }


    public SysDicCodeItem getBscDicCodeItemListByTypeCodeAndItemCode(String typeCode, String itemCode) {
        return bscDicCodeItemMapper.getBscDicCodeItemListByTypeCodeAndItemCode(typeCode,itemCode);
    }

    public List<SysDicCodeItem> listItem(SysDicCodeItem bscDicCodeItem,String keyword) {
        PageHelper.startPage(bscDicCodeItem.getPage(),bscDicCodeItem.getRows());
        Example example=new Example(SysDicCodeItem.class);
        Example.Criteria criteria = example.createCriteria();
        if(bscDicCodeItem.getTypeId()!=null){
            criteria.andEqualTo("typeId",bscDicCodeItem.getTypeId());
        }
        if(StringUtils.isNotBlank(keyword)){
            criteria.andLike("itemCode","%"+keyword+"%");
            criteria.orLike("itemName","%"+keyword+"%");
        }
        example.orderBy("sortNo");
        return bscDicCodeItemMapper.selectByExample(example);
    }

    public List<SysDicCodeType> listType(SysDicCodeType bscDicCodeType,String keyword) {
        PageHelper.startPage(bscDicCodeType.getPage(),bscDicCodeType.getRows());
        Example example=new Example(SysDicCodeType.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("createTime").desc();
        if(StringUtils.isNotBlank(keyword)){
            criteria.andLike("typeCode","%"+keyword+"%");
            criteria.orLike("typeName","%"+keyword+"%");
        }
        return bscDicCodeTypeMapper.selectByExample(example);
    }
}
