pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build API Gateway') {
          steps {
            build 'scava-apigateway'
          }
        }
        stage('Build Knowledge Base') {
          steps {
            build 'scava-knowledgebase'
          }
        }
      }
    }
    stage('Publish') {
      steps {
        slackSend(message: 'Build run on ci5', channel: '#ci', baseUrl: 'https://crossminer.slack.com/services/hooks/jenkins-ci/', token: 'GiuSQlJWxlFkrbp3IBElJQOq', tokenCredentialId: 'jenkins')
      }
    }
  }
}