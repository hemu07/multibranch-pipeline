def incrementVersion() {
    echo "incrementing app version..."
    sh "mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit"
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
}
def buildJarFile() {
    echo "Building the application..."  
    sh 'mvn clean package'
}

def buildImage() {
    echo "building the dockerimage.."
                    withCredentials([ 
                        usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'USER', passwordVariable: 'PAT')
                    ]) { 
                        sh "docker build -t hemu07/hemali_repo:${IMAGE_NAME} ."
                        sh "echo ${PAT} | docker login -u ${USER} --password-stdin"
                        sh "docker push hemu07/hemali_repo:${IMAGE_NAME}"
    }
}

def deployApp() {
    echo "Deploying the application..."
}

def commitVersionUpdate() {
    echo "commiting the latest version back to git repo"
        withCredentials([string(credentialsId: 'github', variable: 'GITHUB_TOKEN')])   
                         {
                    sh 'git config --global user.email "jenkins@example.com"'
                    sh 'git config --global user.name "jenkins"'
                    sh 'git status'
                    sh 'git branch'
                    sh 'git config --list'
                    sh "git remote set-url origin  https://${GITHUB_TOKEN}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME}"
                    sh 'git add .'
                    sh 'git commit -m "ci:version bump"'
                    sh 'git push origin HEAD:jenkins-job'
                    }
    }
return this
