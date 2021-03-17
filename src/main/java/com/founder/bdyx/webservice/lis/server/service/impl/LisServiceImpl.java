package com.founder.bdyx.webservice.lis.server.service.impl;

import com.founder.bdyx.webservice.lis.server.service.LisService;
import com.founder.bdyx.webservice.soap.xml.XmlHandleBp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@Component
public class LisServiceImpl implements LisService {

    @Autowired
    XmlHandleBp xmlhandlebp;


    @Override
    public String getPatsInHospital(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"get_pats_in_hospital");
        return outXML;
    }

    @Override
    public String requestOutp(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"request_outp");
        return outXML;
    }

    @Override
    public String requestInp(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"request_inp");
        return outXML;
    }

    @Override
    public String requestRe(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"request_re");
        return outXML;
    }

    @Override
    public String requestLisConfirm(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"lis_confirm");
        return outXML;
    }

    @Override
    public String bloodAndJYCharge(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"blood_charge");
        return outXML;
    }

    @Override
    public String bloodOrder(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"blood_order");
        return outXML;
    }

    @Override
    public String bloodOrderRevoke(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"blood_order_revoke");
        return outXML;
    }

    @Override
    public String bloodInPatient(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"blood_in_patient");
        return outXML;
    }

    @Override
    public String getOrderStatus(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"get_order_status");
        return outXML;
    }

    @Override
    public String lisBarCode(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"lis_bar_code");
        return outXML;
    }

    @Override
    public String lisMergeVessel(String reqParams) {
        String outXML = xmlhandlebp.exec_LIS(reqParams,"lis_merge_vessel");
        return outXML;
    }

//    @Override
//    public String requestBarcode(String reqParams) {
//        return null;
//    }

}
