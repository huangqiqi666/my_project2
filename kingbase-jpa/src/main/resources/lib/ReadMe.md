CMD执行命令以下命令，将第三方jar包加入到本地maven仓库（公司远程仓库可以登录公司远程maven仓库平台然后页面手动上传）
mvn -s "D:\apache-maven-3.6.3\conf\settings_shhai_new.xml" install:install-file -Dmaven.test.skip=true -DgroupId=com.kingbase8 -DartifactId=kingbase8 -Dversion=V008R006C003B0062 -Dpackaging=jar  -Dfile=D:\project\my_project2\kingbase-demo\src\main\resources\lib\kingbase8-8.6.0.jar 
