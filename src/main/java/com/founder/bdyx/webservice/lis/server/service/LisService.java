package com.founder.bdyx.webservice.lis.server.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.Map;

@WebService(
        targetNamespace = "http://service.lis.com")
public interface LisService {

    @WebMethod(operationName = "get_pats_in_hospital")
    String getPatsInHospital(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "request_outp")
    String requestOutp(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "request_inp")
    String requestInp(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "request_re")
    String requestRe(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "lis_confirm")
    String requestLisConfirm(@WebParam(name = "reqParams") String reqParams);

//    @WebMethod
//    String requestBarcode(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "blood_charge")
    String bloodAndJYCharge(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "blood_in_patient")
    String bloodInPatient(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "blood_order")
    String bloodOrder(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "blood_order_revoke")
    String bloodOrderRevoke(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "get_order_status")
    String getOrderStatus(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "lis_bar_code")
    String lisBarCode(@WebParam(name = "reqParams") String reqParams);

    @WebMethod(operationName = "lis_merge_vessel")
    String lisMergeVessel(@WebParam(name = "reqParams") String reqParams);
}
