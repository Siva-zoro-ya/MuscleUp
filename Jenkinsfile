pipeline {
    agent {
        docker {
            image 'maven:3.9.5-eclipse-temurin-17'
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        SONAR_URL = 'http://localhost:9000'
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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('local-sonarqube') {
                    sh 'mvn sonar:sonar -Dsonar.projectKey=muscleup -Dsonar.host.url=$SONAR_URL -Dsonar.token=$SONAR_CRED'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Push to DockerHub') {
            steps {
                sh '''
                    echo $DOCKERHUB_CRED_PSW | docker login -u $DOCKERHUB_CRED_USR --password-stdin
                    docker tag $IMAGE_NAME $DOCKERHUB_CRED_USR/$IMAGE_NAME:latest
                    docker push $DOCKERHUB_CRED_USR/$IMAGE_NAME:latest
                '''
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
