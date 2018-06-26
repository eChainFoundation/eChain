#Swagger2的访问路径
http://localhost:8090/swagger-ui.html
#Druid监控页面访问路径
http://localhost:8090/druid/login.html
用户名：root  密码：root
#项目打包命令
mvn clean install
#项目部署命令
nohup java -jar -Xms1024M -Xmx1024M echain-web-1.0.0-SNAPSHOT.jar --spring.profiles.active=prod --server.port=8090 1>/dev/null 2>/dev/null &