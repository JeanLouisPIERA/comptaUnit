pipeline {
		agent {
	        label 'master'
	    }
	
		triggers {
        	cron('0-59/1 * * * *')
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