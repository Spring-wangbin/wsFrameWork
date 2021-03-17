
package com.founder.bdyx.modules.sys.controller;

import com.founder.bdyx.core.entity.ProcessResult;
import com.founder.bdyx.core.result.PageResult;
import com.founder.bdyx.modules.sys.model.SysDicCodeItem;
import com.founder.bdyx.modules.sys.model.SysDicCodeType;
import com.founder.bdyx.modules.sys.service.DictService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.founder.bdyx.core.entity.ProcessResult.ERROR;

/**
 * @author yang.xuefeng
 */

@Api(value ="数据字典管理模块", description = "数据字典管理模块Api",tags = {"数据字典管理模块操作接口"})
@RestController
@RequestMapping("/data/dic")
public class DicController {
    @Autowired
    private DictService dictService;

    @ApiOperation(value = "字典字典管理视图",notes = "字典字典管理视图")
    @GetMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("/modules/data/dic/list");
    }

    @ApiOperation(value = "获取字典数据列表",notes = "获取字典数据列表")
    @GetMapping("/listItem/{id}")
    public PageResult<SysDicCodeItem> listItem(@ApiParam(name = "id",value="字典数据ID",required = true) @PathVariable Integer id,
                                               @ApiParam(name = "bscDicCodeItem",value="字典数据实体",required = true) SysDicCodeItem bscDicCodeItem,
                                               @ApiParam(name = "keyword",value="查询字段",required = false) String keyword){
        bscDicCodeItem.setTypeId(id);
        List<SysDicCodeItem> list = dictService.listItem(bscDicCodeItem, keyword);
        return new PageResult<>(new PageInfo<>(list));
    }

    @ApiOperation(value = "获取字典类型列表",notes = "获取字典数据列表")
    @GetMapping("/listType")
    public PageResult<SysDicCodeType> listType(@ApiParam(name = "bscDicCodeType",value="字典类型实体",required = true) SysDicCodeType bscDicCodeType,
                                               @ApiParam(name = "keyword",value="查询字段",required = false) String keyword){
        List<SysDicCodeType> list=dictService.listType(bscDicCodeType,keyword);
        return new PageResult(new PageInfo(list));
    }

    @ApiOperation(value = "保存字典数据",notes = "保存字典数据")
    @PostMapping("/saveOrUpdateItem")
    public ProcessResult saveOrUpdateItem(@ApiParam(name = "bscDicCodeItem",value="字典数据实体",required = true) SysDicCodeItem bscDicCodeItem){
        try {
            ProcessResult result= new ProcessResult();
            dictService.saveOrUpdateItem(bscDicCodeItem);
            result.setData(bscDicCodeItem.getTypeId());
            return result;
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "删除字典数据",notes = "删除字典数据")
    @PostMapping("/deleteItem/{id}")
    public ProcessResult deleteItem(@ApiParam(name = "id",value="字典数据ID",required = true) @PathVariable Integer id){
        try {
            dictService.deleteItem(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取字典数据信息",notes = "获取字典数据信息")
    @GetMapping("/view/{id}")
    public SysDicCodeItem view(@ApiParam(name = "id",value="字典数据ID",required = true) @PathVariable Integer id){
        return dictService.view(id);
    }

    @ApiOperation(value = "保存字典类型",notes = "保存字典类型")
    @PostMapping("/saveOrUpdateType")
    public ProcessResult saveOrUpdateType(@ApiParam(name = "bscDicCodeType",value="字典类型实体",required = true) SysDicCodeType bscDicCodeType){
        try {
            ProcessResult result=new ProcessResult();
            dictService.saveOrUpdateType(bscDicCodeType);
            result.setData(bscDicCodeType.getId());
            return result;
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }

    }

    @ApiOperation(value = "删除字典类型",notes = "删除字典类型")
    @PostMapping("/deleteType/{id}")
    public ProcessResult deleteType(@ApiParam(name = "id",value="字典类型ID",required = true) @PathVariable Integer id){
        try {
            dictService.deleteType(id);
            return new ProcessResult();
        }catch (Exception e){
            return new ProcessResult(ERROR,e.getMessage().toString());
        }
    }

    @ApiOperation(value = "获取字典类型信息",notes = "获取字典类型信息")
    @GetMapping("/viewType/{id}")
    public SysDicCodeType viewType(@ApiParam(name = "id",value="字典类型ID",required = true) @PathVariable Integer id){
        return dictService.viewType(id);
    }

    /**
     *
     * @param typeCode
     * @return
     */
    @ApiOperation(value = "根据字典类型编码获取字典数据列表",notes = "根据字典类型编码获取字典数据列表")
    @PostMapping(value = "/getBscDicCodeItemListByTypeCode/{typeCode}")
    public List<SysDicCodeItem> getBscDicCodeItemListByTypeCode(@ApiParam(name = "typeCode",value="字典类型编码",required = true) @PathVariable String typeCode){
        return dictService.getBscDicCodeItemListByTypeCode(typeCode);
    }

    /**
     *
     * @param typeCode
     * @param itemCode
     * @return
     */
    @ApiOperation(value = "根据字典类型编码和字典数据编码获取字典数据信息",notes = "根据字典类型编码和字典数据编码获取字典数据信息")
    @PostMapping(value = "/getBscDicCodeItemListByTypeCode/{typeCode}/{itemCode}")
    public SysDicCodeItem getBscDicCodeItemListByTypeCodeAndItemCode(@ApiParam(name = "typeCode",value="字典类型编码",required = true) @PathVariable String typeCode,
                                                                     @ApiParam(name = "itemCode",value="字典数据编码",required = true) @PathVariable String itemCode){
        return dictService.getBscDicCodeItemListByTypeCodeAndItemCode(typeCode,itemCode);
    }


}