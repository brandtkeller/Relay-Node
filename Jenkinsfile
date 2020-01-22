pipeline {
    agent none
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Compile and Package') {
            agent any
            steps {
                sh 'mvn -version'
                sh 'mvn compile'
                sh 'mvn package'
            }
        }
        stage('Unit Test') {
            agent any
            steps {
                sh 'mvn test'
            }
        }
    }
}