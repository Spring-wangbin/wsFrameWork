package com.founder.bdyx.webservice.soap.json.itf;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.bdyx.webservice.soap.json.JsonHandleBp;

@Component
@WebService(targetNamespace = "http://itf.json.soap.webservice.bdyx.founder.com")
public class WsJsonImpl implements IWsJson{

    @Autowired
    JsonHandleBp jsonhandlebp;

    @Override
    public String doBusiness(String inputJson){
        return jsonhandlebp.exec(inputJson);
    }
}
