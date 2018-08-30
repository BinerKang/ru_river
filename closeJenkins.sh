#!/bin/bash
echo "Begin Stop jenkins"
service stop jenkins
sleep 2
echo "Jenkins has stop"
echo "Begin start mysql"
systemctl start mysqld.service
sleep 3
echo "Mysql has started"
echo "Restart tomcat"
/usr/local/tomcat-8.0.51/bin/shutdown.sh
sleep 5
/usr/local/tomcat-8.0.51/bin/catalina.sh start
echo "Tomcat has started"
