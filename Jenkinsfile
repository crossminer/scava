pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build API Gateway') {
          steps {
            build job: 'scava-apigateway', propagate: false
          }
        }
        stage('Build Knowledge Base') {
          steps {
            build job: 'scava-knowledgebase', propagate: false
          }
        }
      }
    }
    stage('Publish') {
      steps {
        slackSend(message: 'Build run on ci5', channel: '#ci', baseUrl: 'http://ci5.castalia.camp:8080/', token: 'GiuSQlJWxlFkrbp3IBElJQOq', tokenCredentialId: 'jenkins', teamDomain: 'https://crossminer.slack.com/services/hooks/jenkins-ci/')
      }
    }
  }
}
