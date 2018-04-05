pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build API Gateway') {
          steps {
            dir(path: 'api-gateway/org.eclipse.scava.apigateway') {
              sh 'mvn validate'
              sh 'mvn compile'
              sh 'mvn test'
            }
            
            junit(testResults: 'api-gateway/org.eclipse.scava.apigateway/target/surefire-reports/*.xml', allowEmptyResults: true)
          }
        }
        stage('Build Knowledge Base') {
          steps {
            dir(path: 'knowledge-base/org.eclipse.scava.knowledgebase/') {
              sh 'mvn validate'
              sh 'mvn compile'
              sh 'mvn test'
            }
            
            junit(testResults: 'knowledge-base/org.eclipse.scava.knowledgebase/target/surefire-reports/*.xml', allowEmptyResults: true)
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