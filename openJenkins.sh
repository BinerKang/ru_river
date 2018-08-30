#!/bin/bash
echo "Begin Stop Mysql"
systemctl stop mysqld.service
echo "Mysql has stopped"
sleep 5
echo "Jenkins starting"
service jenkins start
sleep 5
echo "jenkins has started"
