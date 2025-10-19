pipeline {
    agent any

    tools {
        maven 'Maven 3.9'
        jdk 'JDK17'
    }

    environment {
        SONAR_URL = 'http://localhost:9090'
        SONAR_CRED = credentials('sonarqube-token')
        DOCKERHUB_CRED = credentials('dockerhubid')
        IMAGE_NAME = 'muscleup-app'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Siva-zoro-ya/MuscleUp.git'
            }
        }

        stage('Build with Maven') {
            steps {
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    bat "mvn sonar:sonar -Dsonar.projectKey=muscleup -Dsonar.host.url=%SONAR_URL% -Dsonar.login=%SONAR_CRED%"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "docker build -t %IMAGE_NAME% ."
            }
        }

        stage('Push to DockerHub') {
            steps {
                bat """
                    echo %DOCKERHUB_CRED_PSW% | docker login -u %DOCKERHUB_CRED_USR% --password-stdin
                    docker tag %IMAGE_NAME% %DOCKERHUB_CRED_USR%/%IMAGE_NAME%:latest
                    docker push %DOCKERHUB_CRED_USR%/%IMAGE_NAME%:latest
                """
            }
        }

        stage('Deploy') {
            steps {
                bat "docker run -d -p 7080:7080 --name muscleup-app %DOCKERHUB_CRED_USR%/%IMAGE_NAME%:latest"
            }
        }
    }

    post {
        success {
            echo '✅ Build, Analysis, Dockerization & Deployment Successful!'
        }
        failure {
            echo '❌ Pipeline Failed!'
        }
    }
}
