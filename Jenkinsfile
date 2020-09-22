pipeline {
agent any
	stages {
	
		stage ('Build'){
			steps{
			bat 'mvn clean install'
			}
		}
		
		stage ('Test'){
			steps{
			bat 'mvn test'
			jacoco( 
			      execPattern: 'target/*.exec',
			      classPattern: 'target/classes',
			      sourcePattern: 'src/main/java',
			      exclusionPattern: 'src/test*'
				)
			}
		}
	
	}
}