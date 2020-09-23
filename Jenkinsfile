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
				withMaven{
				bat "mvn clean verify"
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