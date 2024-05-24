pipeline {
    
    agent any

    environment {
      //  BRANCH_NAME = 'jenkins-job'
        NEW_VERSION = '1.3.0'  // use this syntax when we need env var. in more than one stage
       // SERVER_CREDENTIALS = credentials('dummy-server')
    }
    tools {
        maven 'maven-3.9.6'
        //gradle
        //jdk
    }

    parameters {
        string(name: 'VERSION', defaultValue: '', description: 'version to deploy on prod')
        choice(name: 'VERSION', choices: ['1.1.0', '1.1.1', '1.1.2'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description: '')
    }

    stages {

        stage("build") {
            
            steps {
                echo "building the application.. ${NEW_VERSION}"
            }
        }

        stage("test") {

            when { // use to give condition like execute test when branch is dev / test etc
                expression {
                    BRANCH_NAME == 'jenkins-job'  //BRANCH_NAME is the env variable that jenkins provides out of box
                   // params.executeTests execute when true
                }
            }

            steps {
                echo "testing the app.."
            }
        } 

        stage("deploy") {
            input {
                message "select the environment to deploy"
                ok "done"
                parameters {
                    choice(name: 'env', choices: ['dev', 'uat', 'mo'], description: '')
                }
            }
             when {
                expression {
                    BRANCH_NAME == 'master'
                }
             }
            steps {
                echo "deploying the app... with version ${params.VERSION}"

                // usernamePassword is groovy function that allows us to take username, password individually inside object
                //usernamePassword as we defined that typ of crednetials in jenkins ui
               // withCredentials([usernamePassword(credentialsId: 'dummy-server', usernameVariable: 'USER', passwordVariable: 'PASSWORD')]) 
                
                  //  sh "echo ${USER} ${PASSWORD}"
                  sh "echo deployed the app.."
                
            }
    }
}
    post {
        always {       // runs always whether the pipeline fails or succeeds
            echo "executed the pipeline with imageversion .. "
        }
        success {    // executed when pipeline succeeds
            echo "pipeline succeded.. ci is completed"
        }
        failure {   // executed when pipeline fails
            echo "pipeline failed.. error code is sent to slack/mail "
        }
    }
}
