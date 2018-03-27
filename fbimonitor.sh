

#git pull
mvn clean:clean
mvn package

#scp -r fbimonitor-platform/target/fbimonitor-platform/WEB-INF/* fbinew@10.0.0.37:/home/fbinew/tomcat/tomcat-platform/webapps/fbimonitor-platform/WEB-INF/
#scp -r fbimonitor-platform/target/fbimonitor-platform/static/* fbinew@10.0.0.37:/home/fbinew/tomcat/tomcat-platform/webapps/fbimonitor-platform/static/
#ssh fbinew10.0.0.37 '/etc/init.d/tomcat-platform stop'
#ssh fbinew@10.0.0.37 '/etc/init.d/tomcat-platform start'
 
#scp -r fbimonitor-admin/target/fbimonitor-admin/WEB-INF/* fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-monitor/webapps/fbimonitor-admin/WEB-INF/
#scp -r fbimonitor-admin/target/fbimonitor-admin/static/admin_js/* fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-monitor/webapps/fbimonitor-admin/static/admin_js/
#scp -r fbimonitor-admin/target/fbimonitor-admin/static/comm/* fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-monitor/webapps/fbimonitor-admin/static/comm/
#scp -r fbimonitor-admin/target/fbimonitor-admin/static/fbi_static/* fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-monitor/webapps/fbimonitor-admin/static/fbi_static/
#scp -r fbimonitor-admin/target/fbimonitor-admin/static/umeditor fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-monitor/webapps/fbimonitor-admin/static/
#ssh fbisystem@10.0.0.37 '/etc/init.d/tomcat-monitor stop'
#ssh fbisystem@10.0.0.37 '/etc/init.d/tomcat-monitor start'


scp -r fbimonitor-web/target/fbimonitor-web/WEB-INF/* fbisystem@10.0.0.37:/home/fbisystem/tomcat/tomcat-web/webapps/fbimonitor-web/WEB-INF/
#ssh fbisystem@10.0.0.37 '/etc/init.d/tomcat-web stop'
#ssh fbisystem@10.0.0.37 '/etc/init.d/tomcat-web start'


#scp -r fbimonitor-interface/target/fbimonitor-interface/WEB-INF/* tomcat@10.0.0.31:/opt/tomcat/tomcat-interface/webapps/fbimonitor-interface/WEB-INF/
#ssh tomcat@10.0.0.31 '/etc/init.d/tomcat-interface stop'
#ssh tomcat@10.0.0.31 '/etc/init.d/tomcat-interface start'

