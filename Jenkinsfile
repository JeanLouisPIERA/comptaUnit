pipeline {
		agent {
	        label 'master'
	    }
	
		triggers {
        	pollSCM('0-59/1 * * * *')
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