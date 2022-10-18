#!/bin/bash

PROCESS_NAME=SiteCodePrj-0.0.1-SNAPSHOT.jar

ps -ef | grep $PROCESS_NAME | grep -v grep | awk '{print "kill -9 "$2}' | sh
