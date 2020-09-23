pipeline {
	agent any
	
		triggers {
        	pollSCM('H/1 * * * *')
    	}
    	
		stages {
		
			stage ('Build'){
				steps{
				bat 'mvn clean install'
				}
			}
			
			stage ('Test'){
				steps{
				bat 'mvn test'
				}
			}
			
		}
}