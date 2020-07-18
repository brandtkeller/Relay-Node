pipeline {
   agent none
 
   environment {
         HOME_REPO = 'http://192.168.0.122:32600/brandtkeller/Relay-Node.git'
         GITHUB_REPO = 'github.com/brandtkeller/Relay-Node.git'
         REGISTRY = '192.168.0.128:5000/'
         IMAGE = ''
         PROJECT = 'Relay-Node'
    }

   stages {
      // On push to development branches, build and scan test image
      stage('Development build & push') {
          agent { node { label 'docker' } }
          options { skipDefaultCheckout true }
          when { not { branch 'master' } }
          steps {
            sh 'rm -rf *'
            sh 'git clone $HOME_REPO'
            sh 'cd $PROJECT && mvn test'
          }
      }
      // On push to master, build prod image and scan

      stage('Master build & push') {
          agent { node { label 'docker' } }
          options { skipDefaultCheckout true }
          when { branch 'master' }
          steps {
             sh 'rm -rf *'
             sh 'git clone $HOME_REPO'
             sh 'cd $PROJECT && mvn test'
          }
      }

      stage('Mirror to public Github') {
         agent any
         options { skipDefaultCheckout true }
         when { branch 'master' }
         steps {
            catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
               withCredentials([usernamePassword(credentialsId: 'git_creds', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                            sh 'rm -rf *'
                            sh 'git clone --mirror $HOME_REPO'
               }
               withCredentials([usernamePassword(credentialsId: 'github_creds', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                           dir("${PROJECT}.git"){
                                 sh 'git remote add --mirror=fetch github https://$GIT_USERNAME:$GIT_PASSWORD@$GITHUB_REPO'
                                 sh 'git push github --all'
                           }
               }
            }
         }
      }
   }
}