pipeline {
    agent any

    tools {
        maven 'Maven3.9.9'
        jdk 'JDK17'
    }

    environment {
        SONAR_URL = 'http://localhost:9090'
        SONAR_CRED = credentials('sonarqube-token')  // Create this in Jenkins → Manage Credentials
        DOCKERHUB_CRED = credentials('dockerhubid') // Jenkins credentials for DockerHub
        IMAGE_NAME = 'muscleup-app'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-username/MuscleUp.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=muscleup -Dsonar.host.url=$SONAR_URL -Dsonar.login=$SONAR_CRED'
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
                script {
                    sh 'docker build -t $IMAGE_NAME .'
                }
            }
        }

        stage('Push to DockerHub') {
            steps {
                script {
                    sh """
                        echo $DOCKERHUB_CRED_PSW | docker login -u $DOCKERHUB_CRED_USR --password-stdin
                        docker tag $IMAGE_NAME $DOCKERHUB_CRED_USR/$IMAGE_NAME:latest
                        docker push $DOCKERHUB_CRED_USR/$IMAGE_NAME:latest
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker run -d -p 7080:7080 --name muscleup-app $DOCKERHUB_CRED_USR/$IMAGE_NAME:latest'
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
