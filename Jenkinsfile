pipeline {
		agent {
	        label 'master'
	    }
	
		triggers {
        	cron('H/1 * * * *')
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