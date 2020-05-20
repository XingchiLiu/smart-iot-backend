node{
    stage('git clone'){
        checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'edd7519f-35d1-4dc9-a28e-06536efa2d97', url: 'https://github.com/XingchiLiu/smart-iot-backend.git']]])
    }
    stage('mvn test'){
        withMaven(maven: 'maven3.5.4') {
                sh "mvn test"
        }
    }
    stage('mvn build'){
        withMaven(maven: 'maven3.5.4') {
                sh "mvn clean install -Dmaven.test.skip=true"
        }
    }
    stage('mvn run'){
		withEnv(['JENKINS_NODE_COOKIE=dontkillme']) {
		    sh "cp -f start_iot_backend.sh /home/lxc/script/start_iot_backend.sh"
			sh "cp -f target/iot-0.0.1-SNAPSHOT.jar /home/lxc/webapp/smart-iot-backend.jar"
			sh "/home/lxc/script/start_iot_backend.sh"
		}
    }
}