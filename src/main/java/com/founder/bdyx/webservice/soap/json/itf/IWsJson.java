package com.founder.bdyx.webservice.soap.json.itf;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@SOAPBinding(use=SOAPBinding.Use.ENCODED, style= SOAPBinding.Style.RPC)
@WebService(targetNamespace = "http://itf.json.soap.webservice.bdyx.founder.com",endpointInterface = "com.founder.bdyx.webservice.soap.json.itf.IWsJson")
public interface IWsJson {
    @WebMethod
    @WebResult(name = "out")
    String doBusiness (@WebParam(name = "inputJson") String inputXML);
}
