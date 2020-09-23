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
	                    step( [ $class: 'JacocoPublisher'
	                     ] )
	                     publishCoverage(
						  failUnhealthy: true, 
						  calculateDiffForChangeRequests: true,
						  failBuildIfCoverageDecreasedInChangeRequest: true,
						  failNoReports: true)
	                     step( [ $class: 'JacocoPublisher'
	                     ] )
	                    }
					}
				}
				
				
        
		}
		
        
        
 } 