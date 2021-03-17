package com.founder.bdyx.modules.sys.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.founder.bdyx.core.util.DateUtil;
import com.founder.bdyx.webservice.core.domain.WebsvrLog;
import com.founder.bdyx.webservice.core.mapper.WebsvrLogMapper;
import com.github.pagehelper.PageHelper;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import tk.mybatis.mapper.entity.Example;

/**
* @Description 请求日志
* @author yang.xuefeng
* @version 创建时间：2019年12月22日 下午2:11:22
*/
@Service
@Transactional
public class WsLogService {

    @Autowired
    private WebsvrLogMapper wsLogMapper;


    public void insert(WebsvrLog sysLog) {
        wsLogMapper.insert(sysLog);
    }

    public List<WebsvrLog> getListByPage(WebsvrLog websvrLog, String keyword, String requestDate){
        PageHelper.startPage(websvrLog.getPage(),websvrLog.getRows());
        Example example=new Example(WebsvrLog.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(requestDate)){
            Date date = DateUtil.strToDate(requestDate);
            criteria.andBetween("happen_date",DateUtil.formatDate(date),DateUtil.getNextDay(DateUtil.formatDate(date),"1"));
        }

        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("request_xml",keyword);
//            criteria.orLike("response_xml",keyword);
        }
        example.orderBy("happen_date").desc();
        return wsLogMapper.selectByExample(example);
    }

    public File getExcel(List<WebsvrLog> logList) {
            File file  = new File("d:\\springboot\\webServiceLog\\"+ System.currentTimeMillis() +".xls");
            WritableWorkbook workbook = null;
            try {
                workbook = Workbook.createWorkbook(file);
                WritableSheet sheet = workbook.createSheet("sheet1", 0);
                WritableCellFormat wcf = new WritableCellFormat();
                wcf.setBackground(Colour.YELLOW);
                sheet.addCell(new Label(0, 0, "请求时间", wcf));
                sheet.addCell(new Label(1, 0, "请求方法", wcf));
                sheet.addCell(new Label(2, 0, "请求参数"  , wcf));
                sheet.addCell(new Label(3, 0, "响应时间" ,wcf));
                sheet.addCell(new Label(4, 0, "返回结果"  , wcf));
                sheet.addCell(new Label(5, 0, "执行时长(毫秒)" , wcf));
                sheet.addCell(new Label(6, 0, "IP地址" , wcf));
                sheet.addCell(new Label(7, 0, "请求状态"   , wcf));
                sheet.setColumnView(0, 20);
                sheet.setColumnView(1, 20);
                sheet.setColumnView(2, 60);
                sheet.setColumnView(3, 60);
                sheet.setColumnView(4, 100);
                sheet.setColumnView(5, 20);
                sheet.setColumnView(6, 20);
                sheet.setColumnView(7, 20);
                for (int row = 0; row < logList.size(); row++) {
                	sheet.addCell(new Label(0, row+1, DateUtil.formatDate(logList.get(row).getHappen_date(),"yyyy-MM-dd hh:mm:ss")));
                    sheet.addCell(new Label(1, row+1, logList.get(row).getRequest_method()));
                    sheet.addCell(new Label(2, row+1, logList.get(row).getRequest_xml()));
                    sheet.addCell(new Label(3, row+1, DateUtil.formatDate(logList.get(row).getResonse_date(),"yyyy-MM-dd hh:mm:ss")));
                    sheet.addCell(new Label(4, row+1, logList.get(row).getRequest_xml()));
                    sheet.addCell(new Label(5, row+1, logList.get(row).getEls_time().toString()));
                    sheet.addCell(new Label(6, row+1, logList.get(row).getClient_ip()));
                    sheet.addCell(new Label(7, row+1, logList.get(row).getExecute_message()));

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }finally {
                if (workbook != null) {
                    try {
                        workbook.write();
                        workbook.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }
                }
            }

            return file;
    }

    public List<WebsvrLog> getList(WebsvrLog websvrLog, String keyword,String requestDate) {
        Example example=new Example(WebsvrLog.class);
        Example.Criteria criteria = example.createCriteria();
        boolean isMaster=false;
        if(StringUtils.isNotBlank(requestDate)){
            Date date = DateUtil.strToDate(requestDate);
            criteria.andBetween("happen_date",DateUtil.formatDate(date),DateUtil.getNextDay(DateUtil.formatDate(date),"1"));
        }
        if(StringUtils.isNotBlank(keyword)){
            keyword="%"+keyword+"%";
            criteria.andLike("request_xml",keyword);
//            criteria.orLike("response_xml",keyword);
        }
        example.orderBy("happen_date").desc();
        return wsLogMapper.selectByExample(example);
    }
}
