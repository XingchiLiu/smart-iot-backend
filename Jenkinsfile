pipeline{
    agent any

    stages{
            stage('mvn build'){
                steps{
                    withMaven(maven: 'maven3.5.4') {
                                            sh "mvn clean install -Dmaven.test.skip=true"
                    }
                }
            }
            stage('mvn run'){
        		steps{
        		    withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
                            		    sh "cp -f start_iot_backend.sh /home/lxc/script/start_iot_backend.sh"
                            			sh "cp -f target/iot-0.0.1-SNAPSHOT.jar /home/lxc/webapp/smart-iot-backend.jar"
                            			sh "/home/lxc/script/start_iot_backend.sh"
                     }
        		}
            }
    }
}