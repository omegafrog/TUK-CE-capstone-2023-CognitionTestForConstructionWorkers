pipeline {
  agent {
    docker {
      image 'jiwoo2211/cogtest'
      args '-p 8080:8080'
    }

  }
  stages {
    stage('build') {
      steps {
        sh 'sudo ./gradlew build'
      }
    }

    stage('run') {
      steps {
        sh './gradlew bootRun'
      }
    }

  }
}