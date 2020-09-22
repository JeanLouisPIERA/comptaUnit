pipeline {
	agent any
		stages {
		
			stage ('Build'){
				steps{
				bat 'mvn clean install'
				} post {
					success {
						jacoco(
						    execPattern: '**/path_to_file/jacoco.exec',
						    classPattern: '**/coverage/**',
						    sourcePattern: '**/coverage/**',
						    inclusionPattern: '**/*.class'
						)
					}
				}
			}
			
			stage ('Test'){
				steps{
				bat 'mvn test'
				}
			}
		
		}
}