package com.founder.bdyx.webservice.soap.xml.itf;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.founder.bdyx.webservice.soap.xml.XmlHandleBp;

@Component
@WebService(targetNamespace = "http://itf.xml.soap.webservice.bdyx.founder.com")
public class WsXmlImpl implements IWsXml{

    @Autowired
    XmlHandleBp xmlhandlebp;

    //TODO:示例参考代码
    @Override
    public String doBusiness(String inputXML){
        return xmlhandlebp.exec(inputXML);
    }

	@Override
	public String SyncChargeFlag(String inputXML) {
		return xmlhandlebp.exec(inputXML);
	}
}
