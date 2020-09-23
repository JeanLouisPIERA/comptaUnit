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
					bat 'mvn test'
					}
					post {
	                success {
	                    junit '**/target/surefire-reports/*.xml'
	                    step( [ $class: 'JacocoPublisher', 
	 
	                    
		                classPattern: '**/build/classes',
		                sourcePattern: 'src/main/java',
		                exclusionPattern: 'src/test*'
    
	                     ] )
	                     
	                    }
					}
				}
				
				
        
		}
		
        
        
 } 