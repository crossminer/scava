pipeline {
  agent any
  stages {
    stage('error') {
      steps {
        echo 'Building API Gateway'
        dir(path: 'api-gateway/org.eclipse.scava.apigateway') {
          sh 'mvn test'
        }
        
        sh 'mvn validate'
        sh 'mvn build'
        sh 'mvn test'
      }
    }
  }
  post {
    always {
      junit 'target/surefire-reports/*.xml'
      
    }
    
  }
}