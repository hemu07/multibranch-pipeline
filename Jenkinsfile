def gv

pipeline {
    agent any
    tools {
        maven 'maven-3.9.6'
    }

    stages {
        stage('init') {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
          stage('test app') {
            steps {
                script {
                    gv.testApp()
                    }
                }
            }
        
        stage('build jar') {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    gv.buildJarFile()
                }
            }
        }
        stage('build docker image') {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                    gv.buildImage()
                    }
                }
            }
        
        stage('deploying the app') {
            when {
                expression {
                    BRANCH_NAME == 'master'
                }
            }
            steps {
                script {
                   gv.deployApp()
                }
            }
        }   
    }
}
