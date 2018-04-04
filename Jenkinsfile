pipeline {
  agent any
  stages {
    stage('') {
      steps {
        echo 'Building API Gateway'
        dir(path: 'api-gateway/org.eclipse.scava.apigateway')
        sh('mvn validate build test')
      }
    }
  }
  post {
    always {
      junit 'target/surefire-reports/*.xml'
    }
  }
}
