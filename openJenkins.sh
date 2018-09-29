#!/bin/bash
echo "Begin Stop Mysql"
systemctl stop mysqld.service
echo "Mysql has stopped"
sleep 5
echo "Jenkins starting"
service jenkins start
sleep 5
echo "jenkins has started"
echo "close redis"
ps -ef |grep redis |awk '{print $2}'| xargs kill -9
echo "Redis has closed"