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
        stage('increment version') {
            steps {
                script {
                    gv.incrementVersion()
                }
            }
        }
        stage('build jar') {
            steps {
                script {
                    gv.buildJarFile()
                }
            }
        }
        stage('build docker image') {
            steps {
                script {
                    gv.buildImage()
                    }
                }
            }
        
        stage('deploying the app') {
            steps {
                script {
                   gv.deployApp()
                }
            }
        }
         stage('commit updated version back to git repo') {
              environment {
            GIT_REPO_NAME = "demo-java-maven-app"
            GIT_USER_NAME = "hemu07"
        }
            steps {
                script {
                gv.commitVersionUpdate()
                }
            }
        }

    }
}
