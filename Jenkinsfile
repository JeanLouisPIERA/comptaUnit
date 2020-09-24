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
	                    step( [ 
						  $class: 'JacocoPublisher',
						  publishers:
							  [- jacoco:
							      targets:
							        - instruction:
							            healthy: 0.7
							            unhealthy: 0.1
							        - branch:
							            healthy: 8.7
							            unhealthy: 0.1
							        - complexity:
							            healthy: 0.7
							            unhealthy: 0.1
							        - line:
							            healthy: 0.7
							            unhealthy: 0.1
							        - method:
							            healthy: 0.7
							            unhealthy: 0.1
							        - class:
							            healthy: 0.7
							            unhealthy: 0.1]
								])
	                    }
					}
				}
				
				
        
		}
		
        
        
 } 