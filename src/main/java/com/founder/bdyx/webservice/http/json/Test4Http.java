package com.founder.bdyx.webservice.http.json;

import com.founder.bdyx.webservice.core.service.IHttpLog;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Api(value = "testJSON",tags = {"测试信息"},produces = "application/json")
@WebServlet(name = "testServlet",urlPatterns = "/test")
public class Test4Http extends HttpServlet {

    private static Logger _log = Logger.getLogger(Test4Http.class);

    @Autowired
    IHttpLog httpLog;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        _log.info("test数据信息");
        String aa = request.getParameter("id");
        _log.info(aa);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
