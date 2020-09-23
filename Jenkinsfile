pipeline {
	agent any
	
		triggers {
        	cron('H/10 * * * *')
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