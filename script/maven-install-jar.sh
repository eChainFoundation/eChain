#!/bin/bash
#
# 添加外部无法获取的jar包

echo "开始将所以来的包导入到maven..."

mvn install:install-file -Dfile=../lib/jai_core.jar -DgroupId=javax.media -DartifactId=jai_core -Dversion=1.1.3 -Dpackaging=jar
mvn install:install-file -Dfile=../lib/icepdf-core.jar -DgroupId=org.icepdf.core -DartifactId=icepdf-core -Dversion=6.2.5 -Dpackaging=jar
mvn install:install-file -Dfile=../lib/jave-1.0.2.jar -DgroupId=it.sauronsoftware -DartifactId=jave -Dversion=0.0.1 -Dpackaging=jar
