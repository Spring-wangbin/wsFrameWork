package com.founder.bdyx.webservice.core.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "f_websvr_config")
public class WebsvrConfig implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(insertable=false)  
	private Integer id;

    private String func_id;

    private String func_name;

    private Integer invoke_type;

    private String invoke_proc;

    private String parmas_list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunc_id() {
        return func_id;
    }

    public void setFunc_id(String func_id) {
        this.func_id = func_id;
    }

    public String getFunc_name() {
        return func_name;
    }

    public void setFunc_name(String func_name) {
        this.func_name = func_name;
    }

    public Integer getInvoke_type() {
        return invoke_type;
    }

    public void setInvoke_type(Integer invoke_type) {
        this.invoke_type = invoke_type;
    }

    public String getInvoke_proc() {
        return invoke_proc;
    }

    public void setInvoke_proc(String invoke_proc) {
        this.invoke_proc = invoke_proc;
    }

    public String getParmas_list() {
        return parmas_list;
    }

    public void setParmas_list(String parmas_list) {
        this.parmas_list = parmas_list;
    }
}
