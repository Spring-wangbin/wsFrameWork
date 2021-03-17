package com.founder.bdyx.webservice.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.founder.bdyx.core.entity.BaseEntity;

@Table(name = "f_websvr_log")
public class WebsvrLog extends BaseEntity implements Serializable {

    @Id
	@Column(insertable=false)
    private Integer log_id;
    private Date happen_date;
    private String request_xml;
    private String request_method;
    private Date resonse_date;
    private String response_xml;
    private String server_ip;
    private Long els_time;
    private String client_ip;
    private String execute_flag;
    private String execute_message;

    public Integer getLog_id() {
        return log_id;
    }

    public void setLog_id(Integer log_id) {
        this.log_id = log_id;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public Date getHappen_date() {
        return happen_date;
    }

    public void setHappen_date(Date happen_date) {
        this.happen_date = happen_date;
    }

    public String getRequest_xml() {
        return request_xml;
    }

    public void setRequest_xml(String request_xml) {
        this.request_xml = request_xml;
    }

    public Date getResonse_date() {
        return resonse_date;
    }

    public void setResonse_date(Date resonse_date) {
        this.resonse_date = resonse_date;
    }

    public String getResponse_xml() {
        return response_xml;
    }

    public void setResponse_xml(String response_xml) {
        this.response_xml = response_xml;
    }

    public String getServer_ip() {
        return server_ip;
    }

    public void setServer_ip(String server_ip) {
        this.server_ip = server_ip;
    }


    public Long getEls_time() {
        return els_time;
    }

    public void setEls_time(Long els_time) {
        this.els_time = els_time;
    }


    public String getExecute_flag() {
        return execute_flag;
    }

    public void setExecute_flag(String execute_flag) {
        this.execute_flag = execute_flag;
    }

    public String getExecute_message() {
        return execute_message;
    }

    public void setExecute_message(String execute_message) {
        this.execute_message = execute_message;
    }

	public String getRequest_method() {
		return request_method;
	}

	public void setRequest_method(String request_method) {
		this.request_method = request_method;
	}
}
