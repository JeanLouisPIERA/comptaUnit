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
				jacoco(
				    execPattern: '**/path_to_file/jacoco.exec',
				    classPattern: '**/coverage/**',
				    sourcePattern: '**/coverage/**',
				    inclusionPattern: '**/*.class'
				)
                }
			}
			
			stage ('Test'){
				steps{
				bat 'mvn test'
				}
			}
			
			
		}
}