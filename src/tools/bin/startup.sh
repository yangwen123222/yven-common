#!/bin/sh
export BASEDIR=..
export CLASSPATH=$BASEDIR/conf
nohup java -server -classpath $CLASSPATH -Dbase.dir=$BASEDIR -Djava.net.preferIPv4Stack=true -jar $BASEDIR/druid_test_java.jar > /dev/null  2>&1 & 
echo $! > $BASEDIR/server.pid
echo 'druid_test started successful!'
tail -f $BASEDIR/logfile/druid_test/druid_test_log.log
