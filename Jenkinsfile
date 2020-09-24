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
					bat 'mvn clean compile'
	                }
				}
				
				stage ('Test'){
					steps{
					bat 'mvn verify'
					}
					post {
	                always {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ $class: 'JacocoPublisher'] ,
	                    jacoco.execPattern: 'target/*.exec',
			  	        jacoco.classPattern: 'target/classes',
				        jacoco.sourcePattern: 'src/main/java',
				        jacoco.exclusionPattern: 'src/test*'
	                     )
	                    }
					}
				}
				
				
        
		}
		
        
        
 } 