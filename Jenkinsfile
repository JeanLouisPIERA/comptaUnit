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
                    execPattern: '**/target/code-coverage/**.exec',
                    classPattern: '**/target/classes',
                    sourcePattern: '**/src',
                    inclusionPattern: 'com/company/**',
                    changeBuildStatus: true,
                    minimumInstructionCoverage: '70'
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