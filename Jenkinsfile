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
				sh "mvn clean verify"
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