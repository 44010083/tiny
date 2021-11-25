pipeline {
    agent any

    stages {
         stage('WEB编译') {
            steps {
                dir('web'){
                    sh "npm install&&npm run build"
                    sh "rm -Rf app/src/main/resources/static"
                    sh "rm -Rf app/src/main/resources/index.html"
                    sh "cp -R dist/* app/src/main/resources/"
                }
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
                    sh 'docker build -t vinjara/tiny:app-latest .'
                    sh "docker push vinjara/tiny:app-latest"
                }
            }
        }
    }
}
