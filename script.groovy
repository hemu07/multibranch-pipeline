def testApp() {
    echo "testing the app"
    echo "executing pipeline for branch ${BRANCH_NAME}"
}

def buildJarFile() {
    echo "Building the application..."  
    sh 'mvn package'
}

def buildImage() {
    echo "building the dockerimage.."
                    withCredentials([ 
                        usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'USER', passwordVariable: 'PAT')
                    ]) { 
                        sh "docker build -t hemu07/hemali_repo:jma-2.0 . "
                        sh "echo ${PAT} | docker login -u ${USER} --password-stdin"
                        sh "docker push hemu07/hemali_repo:jma-2.0"
    }
}


def deployApp() {
    echo "Deploying the application..."
    echo " some changes to trigger webhook"
}

return this
