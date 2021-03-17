package com.founder.bdyx;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;
@Controller
@EnableWebMvc
@SpringBootApplication(scanBasePackages = "com.founder.bdyx")
//@MapperScan(basePackages = "com.founder.bdyx.modules.*.mapper")
@MapperScan({"com.founder.bdyx.modules.*.mapper","com.founder.bdyx.webservice.**.mapper"})
@EnableScheduling
@EnableCaching
@EnableSwagger2
@ServletComponentScan({"com.founder.bdyx.webservice.http.*"})
public class StartApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {
	private Logger logger= LoggerFactory.getLogger(StartApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("服务启动完成！");
	}

	@RequestMapping({"/","","/toLogin"})
	public String home(){
		return "login";
	}
	
	/**
	 * 解决 RequestContextHolder.getRequestAttributes()空指针异常
	 * @return
	 */
	@Bean
	public RequestContextListener requestContextListener(){
		return new RequestContextListener();
	}
	

}
