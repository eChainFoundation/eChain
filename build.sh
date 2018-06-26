#!/bin/sh

mvn clean install

#18.219.184.29  prod
#scp -r echain-web/target/classes/META-INF/resources/static/* root@18.219.184.29:/var/www/echain/static/
scp echaincloud-web/target/echaincloud-web-1.0.0-SNAPSHOT.jar root@18.219.184.29:/opt/tomcat/


#101.132.124.123  dev
#scp echaincloud-web/target/echaincloud-web-1.0.0-SNAPSHOT.jar root@101.132.124.123:/opt/tomcat/