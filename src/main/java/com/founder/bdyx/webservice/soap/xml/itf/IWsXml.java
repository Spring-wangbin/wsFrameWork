package com.founder.bdyx.webservice.soap.xml.itf;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@SOAPBinding(use=SOAPBinding.Use.ENCODED, style= SOAPBinding.Style.RPC)
@WebService(targetNamespace = "http://itf.xml.soap.webservice.bdyx.founder.com",endpointInterface = "com.founder.bdyx.webservice.soap.xml.itf.IWsXml")
public interface IWsXml {
    @WebMethod
    @WebResult(name = "out")
    String doBusiness (@WebParam(name = "inputXml") String inputXML);
    
    
    //TODO:示例参考代码
    @WebMethod
    @WebResult(name = "out")
    String SyncChargeFlag (@WebParam(name = "inputXml") String inputXML);
}
