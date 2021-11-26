pipeline {
    agent any
    parameters {
      string defaultValue: 'vinjara/tiny:app-latest', name: 'imgName'
      string defaultValue: 'tiny', name: 'appName'
    }
    stages {
         stage('WEB编译') {
            steps {
                dir('web'){
                    sh "npm install&&npm run build"
                }
                sh "rm -Rf app/src/main/resources/static"
                sh "rm -Rf app/src/main/resources/index.html"
                sh "cp -R web/dist/* app/src/main/resources/"
            }
        }
        stage('APP编译') {
            steps {
                dir('app'){
                    //echo 'mvn'
                    sh "mvn org.jacoco:jacoco-maven-plugin:prepare-agent -Dmaven.test.failure.ignore=true clean package"
                }
            }
        }
        stage('单元测试') {
            steps {
                echo 'junit report'
                junit '**/*/surefire-reports/TEST-*.xml'
                
                jacoco buildOverBuild: true, changeBuildStatus: true, deltaLineCoverage: '70', exclusionPattern: '**/model/**/*', sourceExclusionPattern: '**/model/**/*'
            }
        }
        stage('打docker镜像') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'password', usernameVariable: 'username')]) {
                    sh 'echo $password | docker login -u $username --password-stdin'
                    sh 'docker build -t $imgName .'
                    sh "docker push $imgName"
                }
            }
        }
        stage('部署') {
            steps {
                withCredentials([usernamePassword(credentialsId: '172.31.26.159', passwordVariable: 'password', usernameVariable: 'username')]) {
                    script {
                      def remote = [:]
                      remote.user = username
                      remote.password = password
                      //remote.identityFile = identity
                      remote.host = '172.31.26.159'
                      remote.name = '172.31.26.159'
                      remote.allowAnyHosts = true
                      sshCommand remote: remote, command: "docker pull $imgName"
                      try{
                        sshCommand remote: remote, command: 'docker rm -f $appName'
                      }catch (e){
                      }
                      sshCommand remote: remote, command: "docker run -d --name $appName -p 8080:8080 $imgName"
                  }
                }
            }
        }
    }
}
