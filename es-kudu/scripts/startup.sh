#/bin/bash

base_path=$(cd `dirname $0`;pwd)

cd $base_path

nohup java -jar SiteCodePrj-0.0.1-SNAPSHOT.jar &




