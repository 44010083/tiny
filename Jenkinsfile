pipeline {
    agent any

    stages {
        stage('拉取代码') {
            steps {
                //echo 'git'
                git([branch:'main',url:'https://github.com/44010083/tiny.git'])
            }
        }
         stage('WEB编译') {
            steps {
                dir('web'){
                    //echo 'mvn'
                    sh "npm install&&npm run build"
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
                
                jacoco buildOverBuild: true, changeBuildStatus: true, deltaLineCoverage: '70'
            }
        }
    }
}
