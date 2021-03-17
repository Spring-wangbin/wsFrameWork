
项目说明:该项目为交付一部通用接口服务框架。采用Aspect切面技术保存系统操作日志。集成FTP实现文件上传下载。Swagger文档提供api管理。<br/>
前端技术：bootstarp+jquery+framaker模板<br/>
后端技术：springboot+Mybatis+（redis+Cache）缓存+shiro权限控制<br/>
数据库：sqlserver <br/>

功能结构:<br/>
>系统管理<br/>
>>用户管理<br/>
>>角色管理<br/>
>>菜单管理<br/>
>>部门管理<br/>

>配置管理<br/>
>>数据字典管理<br/>
>>定时器管理<br/>

>接口管理<br/>
>>服务地址<br/>

>系统监控<br/>
>>服务监控<br/>
>>系统日志<br/>
>>服务日志<br/>

>文件管理<br/>
>>文件列表<br/>

部署说明：<br/>
一. 环境要求<br/>
sqlserver 2008，JDK8，Maven，FTP，redis
<br/>
二. 操作步骤<br/>
1.下载项目到本地后，查看application.properties（spring.profiles.active=test，其中test对应的表示application-test.properties映射文件），application-dev.properties（本地开发库），application-prod.properties(线上测试库)文件，修改对应配置。<br/>
2.数据库初始数据见目录doc/sql/f_ws_fw.sql<br/>
3.运行StartApplication启动类。<br/>
4.注：若需要redis，则需本地运行redis,对应修改application.properties文件中的redis配置，另外将spring.redis.isopen=false设置为true.<br/>
5.访问地址为 http://localhost:9009/  
<br/>
系统管理员角色：sys_admin(123456)<br/>      
超级管理员角色：admin(123456)<br/>
<br/>
FTP服务器部署:<br/>
FTP经验：https://www.cnblogs.com/popfisher/p/7992036.html
<br/>

部署文档<br>
# 部署
1. 停止应用<br>

# 命令，查询pid
ps aux |grep 9009<br>

# 找到对应的pid，然后kill掉
kill -9 xxx<br>

# 例子
[root@cqydtest web]# ps aux |grep 9009<br>
root     22277  2.9 14.5 5170228 1193768 pts/1 Sl   15:27   2:02 java -jar wsFrameWork-0.0.1-SNAPSHOT.jar<br>
root     22537  0.0  0.0 103268   856 pts/1    S+   16:36   0:00 grep 9091<br>
[root@cqydtest web]# kill -9 22277<br>

2. 启动应用<br>
# 命令
nohup java -jar wsFrameWork-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --server.port=9009 &<br>
