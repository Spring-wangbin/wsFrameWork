package com.founder.bdyx.webservice.soap.config;

import javax.xml.ws.Endpoint;

import com.founder.bdyx.webservice.lis.server.service.LisService;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决使用cxf后原有的http不能访问
 */
@Configuration
@ConditionalOnWebApplication
public class SoapWsConfig {

    @Value("${cxf.path}")
    private String cxf_servlet_path;

    @Autowired
    private Bus bus;

    @Autowired
    LisService lisService;

    public Endpoint endpointLis(){
        EndpointImpl endpoint = new EndpointImpl(bus,lisService);
        endpoint.publish("/lis");
        return endpoint;
    }

    public String getCxf_servlet_path() {
        return cxf_servlet_path;
    }

    public void setCxf_servlet_path(String cxf_servlet_path) {
        this.cxf_servlet_path = cxf_servlet_path;
    }
}
