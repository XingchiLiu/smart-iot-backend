echo "Stopping SpringBoot Application"
pid=`ps -ef | grep smart-iot-backend.jar | grep -v grep | awk '{print $2}'`
if [ -n "$pid" ]
then
   kill -9 $pid
fi

BUILD_ID=dontKillMe
JENKINS_NODE_COOKIE=dontKillMe
nohup java -jar /home/lxc/webapp/smart-iot-backend.jar &
echo "Webapp running..."