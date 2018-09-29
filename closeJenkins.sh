#!/bin/bash
echo "Begin Stop jenkins"
service jenkins stop 
sleep 2
echo "Jenkins has stop"
echo "Begin start mysql"
systemctl start mysqld.service
sleep 3
echo "Mysql has started"
echo "start redis"
cd /usr/local/redis-3.2.9/
src/redis-server redis.conf
echo "redis started"
echo "Restart tomcat"
/usr/local/tomcat-8.0.51/bin/shutdown.sh
sleep 5
/usr/local/tomcat-8.0.51/bin/catalina.sh start
echo "Tomcat has started"
