pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        echo 'Building API Gateway'
        dir(path: 'api-gateway/org.eclipse.scava.apigateway') {
          sh 'mvn validate'
          sh 'mvn compile'
          sh 'mvn test'
        }
        
        junit(testResults: 'knowledge-base/org.eclipse.scava.knowledgebase/target/surefire-reports/*.xml', allowEmptyResults: true)
      }
    }
  }
  post {
    always {
      junit 'target/surefire-reports/*.xml'
      
    }
    
  }
}